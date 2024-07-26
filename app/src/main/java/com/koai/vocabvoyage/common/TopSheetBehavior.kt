package com.koai.vocabvoyage.common

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.ClassLoaderCreator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.NestedScrollType
import androidx.customview.view.AbsSavedState
import androidx.customview.widget.ViewDragHelper
import com.google.android.material.R
import java.lang.ref.WeakReference
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * An interaction behavior plugin for a child view of [CoordinatorLayout] to make it work as
 * a bottom sheet.
 */
@Suppress("UNCHECKED_CAST")
class TopSheetBehavior<V : View>(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<V>(context, attrs) {
    /**
     * Callback for monitoring events about bottom sheets.
     */
    abstract class TopSheetCallback {
        /**
         * Called when the bottom sheet changes its state.
         *
         * @param bottomSheet The bottom sheet view.
         * @param newState    The new state. This will be one of [.STATE_DRAGGING],
         * [.STATE_SETTLING], [.STATE_EXPANDED],
         * [.STATE_COLLAPSED], or [.STATE_HIDDEN].
         */
        abstract fun onStateChanged(bottomSheet: View, @State newState: Int)

        /**
         * Called when the bottom sheet is being dragged.
         *
         * @param bottomSheet The bottom sheet view.
         * @param slideOffset The new offset of this bottom sheet within its range, from 0 to 1
         * when it is moving upward, and from 0 to -1 when it moving downward.
         */
        abstract fun onSlide(bottomSheet: View, slideOffset: Float)
    }

    @IntDef(*[STATE_EXPANDED, STATE_COLLAPSED, STATE_DRAGGING, STATE_SETTLING, STATE_HIDDEN])
    @Retention(
        AnnotationRetention.SOURCE
    )
    annotation class State

    private val mMaximumVelocity: Float
    private var mPeekHeight = 0
    private var mMinOffset = 0
    private var mMaxOffset = 0
    private var mHideable = false
    var skipCollapsed = false

    @State
    private var mState = STATE_COLLAPSED
    private var mViewDragHelper: ViewDragHelper? = null
    private var mIgnoreEvents = false
    private var mLastNestedScrollDy = 0
    private var mNestedScrolled = false
    private var mViewRef: WeakReference<V?>? = null
    private var mNestedScrollingChildRef: WeakReference<View?>? = null
    private var mCallback: TopSheetCallback? = null
    private var mVelocityTracker: VelocityTracker? = null
    private var mActivePointerId = 0
    private var mInitialY = 0
    private var mTouchingScrollingChild = false
    override fun onSaveInstanceState(parent: CoordinatorLayout, child: V): Parcelable? {
        return SavedState(super.onSaveInstanceState(parent, child), mState)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: V, state: Parcelable) {
        val ss = state as SavedState
        assert(ss.superState != null)
        super.onRestoreInstanceState(parent, child, ss.superState!!)
        // Intermediate states are restored as collapsed state
        mState = if (ss.state == STATE_DRAGGING || ss.state == STATE_SETTLING) {
            STATE_COLLAPSED
        } else {
            ss.state
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child!!.fitsSystemWindows = true
        }
        val savedTop = child!!.top
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection)
        // Offset the bottom sheet
        mMinOffset = max(-child.height.toDouble(), -(child.height - mPeekHeight).toDouble())
            .toInt()
        mMaxOffset = 0
        if (mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, mMaxOffset)
        } else if (mHideable && mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, -child.height)
        } else if (mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, mMinOffset)
        } else if (mState == STATE_DRAGGING || mState == STATE_SETTLING) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.top)
        }
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDragCallback)
        }
        mViewRef = WeakReference(child)
        mNestedScrollingChildRef = WeakReference(findScrollingChild(child))
        return true
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent
    ): Boolean {
        if (!child!!.isShown()) {
            return false
        }
        val action = event.actionMasked
        // Record the velocity
        if (action == MotionEvent.ACTION_DOWN) {
            reset()
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)
        when (action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mTouchingScrollingChild = false
                mActivePointerId = MotionEvent.INVALID_POINTER_ID
                // Reset the ignore flag
                if (mIgnoreEvents) {
                    mIgnoreEvents = false
                    return false
                }
            }

            MotionEvent.ACTION_DOWN -> {
                val initialX = event.x.toInt()
                mInitialY = event.y.toInt()
                val scroll = mNestedScrollingChildRef!!.get()
                if (scroll != null && parent.isPointInChildBounds(scroll, initialX, mInitialY)) {
                    mActivePointerId = event.getPointerId(event.actionIndex)
                    mTouchingScrollingChild = true
                }
                mIgnoreEvents = mActivePointerId == MotionEvent.INVALID_POINTER_ID &&
                        !parent.isPointInChildBounds(child, initialX, mInitialY)
            }
        }
        if (!mIgnoreEvents && mViewDragHelper!!.shouldInterceptTouchEvent(event)) {
            return true
        }
        // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        // it is not the top most view of its parent. This is not necessary when the touch event is
        // happening over the scrolling content as nested scrolling logic handles that case.
        val scroll = mNestedScrollingChildRef!!.get()
        return action == MotionEvent.ACTION_MOVE && scroll != null &&
                !mIgnoreEvents && mState != STATE_DRAGGING &&
                !parent.isPointInChildBounds(
                    scroll,
                    event.x.toInt(),
                    event.y.toInt()
                ) && abs((mInitialY - event.y).toDouble()) > mViewDragHelper!!.touchSlop
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        if (!child!!.isShown()) {
            return false
        }
        val action = event.actionMasked
        if (mState == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
            return true
        }
        if (mViewDragHelper != null) {
            //no crash
            mViewDragHelper!!.processTouchEvent(event)
            // Record the velocity
            if (action == MotionEvent.ACTION_DOWN) {
                reset()
            }
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain()
            }
            mVelocityTracker!!.addMovement(event)
            // The ViewDragHelper tries to capture only the top-most View. We have to explicitly tell it
            // to capture the bottom sheet in case it is not captured and the touch slop is passed.
            if (action == MotionEvent.ACTION_MOVE && !mIgnoreEvents) {
                if (abs((mInitialY - event.y).toDouble()) > mViewDragHelper!!.touchSlop) {
                    mViewDragHelper!!.captureChildView(child, event.getPointerId(event.actionIndex))
                }
            }
        }
        return !mIgnoreEvents
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View,
        target: View, nestedScrollAxes: Int, @NestedScrollType type: Int
    ): Boolean {
        mLastNestedScrollDy = 0
        mNestedScrolled = false
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View,
        dx: Int, dy: Int, consumed: IntArray, @NestedScrollType type: Int
    ) {
        val scrollingChild = mNestedScrollingChildRef!!.get()
        if (target !== scrollingChild) {
            return
        }
        val currentTop = child!!.top
        val newTop = currentTop - dy
        if (dy > 0) { // Upward
            if (!target.canScrollVertically(1)) {
                if (newTop >= mMinOffset || mHideable) {
                    consumed[1] = dy
                    ViewCompat.offsetTopAndBottom(child, -dy)
                    setStateInternal(STATE_DRAGGING)
                } else {
                    consumed[1] = currentTop - mMinOffset
                    ViewCompat.offsetTopAndBottom(child, -consumed[1])
                    setStateInternal(STATE_COLLAPSED)
                }
            }
        } else if (dy < 0) { // Downward
            // Negative to check scrolling up, positive to check scrolling down
            if (newTop < mMaxOffset) {
                consumed[1] = dy
                ViewCompat.offsetTopAndBottom(child, -dy)
                setStateInternal(STATE_DRAGGING)
            } else {
                consumed[1] = currentTop - mMaxOffset
                ViewCompat.offsetTopAndBottom(child, -consumed[1])
                setStateInternal(STATE_EXPANDED)
            }
        }
        dispatchOnSlide(child.top)
        mLastNestedScrollDy = dy
        mNestedScrolled = true
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View,
        @NestedScrollType type: Int
    ) {
        if (child!!.top == mMaxOffset) {
            setStateInternal(STATE_EXPANDED)
            return
        }
        if (target !== mNestedScrollingChildRef!!.get() || !mNestedScrolled) {
            return
        }
        val top: Int
        val targetState: Int
        if (mLastNestedScrollDy < 0) {
            top = mMaxOffset
            targetState = STATE_EXPANDED
        } else if (mHideable && shouldHide(child, yVelocity)) {
            top = -child.height
            targetState = STATE_HIDDEN
        } else if (mLastNestedScrollDy == 0) {
            val currentTop = child.top
            if (abs((currentTop - mMinOffset).toDouble()) > abs((currentTop - mMaxOffset).toDouble())) {
                top = mMaxOffset
                targetState = STATE_EXPANDED
            } else {
                top = mMinOffset
                targetState = STATE_COLLAPSED
            }
        } else {
            top = mMinOffset
            targetState = STATE_COLLAPSED
        }
        if (mViewDragHelper!!.smoothSlideViewTo(child, child.left, top)) {
            setStateInternal(STATE_SETTLING)
            ViewCompat.postOnAnimation(child, SettleRunnable(child, targetState))
        } else {
            setStateInternal(targetState)
        }
        mNestedScrolled = false
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout, child: V, target: View,
        velocityX: Float, velocityY: Float
    ): Boolean {
        return target === mNestedScrollingChildRef!!.get() &&
                (mState != STATE_EXPANDED ||
                        super.onNestedPreFling(
                            coordinatorLayout, child, target,
                            velocityX, velocityY
                        ))
    }

    /**
     * Sets the height of the bottom sheet when it is collapsed.
     *
     * @param peekHeight The height of the collapsed bottom sheet in pixels.
     */
    fun setPeekHeight(peekHeight: Int) {
        mPeekHeight = max(0.0, peekHeight.toDouble()).toInt()
        if (mViewRef != null && mViewRef!!.get() != null) {
            mMinOffset = max(
                -mViewRef!!.get()!!.height.toDouble(), -(mViewRef!!.get()!!
                    .height - mPeekHeight).toDouble()
            ).toInt()
        }
    }

    fun setHideable(hideable: Boolean) {
        mHideable = hideable
    }

    /**
     * Sets a callback to be notified of bottom sheet events.
     *
     * @param callback The callback to notify when bottom sheet events occur.
     */
    fun setTopSheetCallback(callback: TopSheetCallback?) {
        mCallback = callback
    }

    @get:State
    var state: Int
        /**
         * Gets the current state of the bottom sheet.
         *
         * @return One of [.STATE_EXPANDED], [.STATE_COLLAPSED], [.STATE_DRAGGING],
         * and [.STATE_SETTLING].
         */
        get() = mState
        /**
         * Sets the state of the bottom sheet. The bottom sheet will transition to that state with
         * animation.
         *
         * @param state One of [.STATE_COLLAPSED], [.STATE_EXPANDED], or
         * [.STATE_HIDDEN].
         */
        set(state) {
            if (state == mState) {
                return
            }
            if (mViewRef == null) {
                // The view is not laid out yet; modify mState and let onLayoutChild handle it later
                if (state == STATE_COLLAPSED || state == STATE_EXPANDED || mHideable && state == STATE_HIDDEN) {
                    mState = state
                }
                return
            }
            val child = mViewRef!!.get() ?: return
            val top: Int
            top = if (state == STATE_COLLAPSED) {
                mMinOffset
            } else if (state == STATE_EXPANDED) {
                mMaxOffset
            } else if (mHideable && state == STATE_HIDDEN) {
                -child.height
            } else {
                throw IllegalArgumentException("Illegal state argument: $state")
            }
            setStateInternal(STATE_SETTLING)
            if (mViewDragHelper!!.smoothSlideViewTo(child, child.left, top)) {
                ViewCompat.postOnAnimation(child, SettleRunnable(child, state))
            }
        }

    private fun setStateInternal(@State state: Int) {
        if (mState == state) {
            return
        }
        mState = state
        val bottomSheet: View? = mViewRef!!.get()
        if (bottomSheet != null && mCallback != null) {
            mCallback!!.onStateChanged(bottomSheet, state)
        }
    }

    private fun reset() {
        mActivePointerId = ViewDragHelper.INVALID_POINTER
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    private fun shouldHide(child: View, yvel: Float): Boolean {
        if (child.top > mMinOffset) {
            // It should not hide, but collapse.
            return false
        }
        val newTop = child.top + yvel * HIDE_FRICTION
        return abs((newTop - mMinOffset).toDouble()) / mPeekHeight.toFloat() > HIDE_THRESHOLD
    }

    private fun findScrollingChild(view: View): View? {
        if (view is NestedScrollingChild) {
            return view
        }
        if (view is ViewGroup) {
            val group = view
            var i = 0
            val count = group.childCount
            while (i < count) {
                val scrollingChild = findScrollingChild(group.getChildAt(i))
                if (scrollingChild != null) {
                    return scrollingChild
                }
                i++
            }
        }
        return null
    }

    private val yVelocity: Float
        private get() {
            mVelocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity)
            return mVelocityTracker!!.getYVelocity(mActivePointerId)
        }
    private val mDragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (mState == STATE_DRAGGING) {
                return false
            }
            if (mTouchingScrollingChild) {
                return false
            }
            if (mState == STATE_EXPANDED && mActivePointerId == pointerId) {
                val scroll = mNestedScrollingChildRef!!.get()
                if (scroll != null && scroll.canScrollVertically(-1)) {
                    // Let the content scroll up
                    return false
                }
            }
            return mViewRef != null && mViewRef!!.get() === child
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            dispatchOnSlide(top)
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING)
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val top: Int
            @State var targetState: Int
            if (yvel > 0) { // Moving up
                top = mMaxOffset
                targetState = STATE_EXPANDED
            } else if (mHideable && shouldHide(releasedChild, yvel)) {
                top = -mViewRef!!.get()!!.height
                targetState = STATE_HIDDEN
            } else if (yvel == 0f) {
                val currentTop = releasedChild.top
                targetState = STATE_EXPANDED
                if (abs((currentTop - mMinOffset).toDouble()) > abs((currentTop - mMaxOffset).toDouble())) {
                    top = mMaxOffset
                } else {
                    top = mMinOffset
                    targetState = STATE_COLLAPSED
                }
            } else {
                top = mMinOffset
                targetState = STATE_COLLAPSED
            }
            if (mViewDragHelper!!.settleCapturedViewAt(releasedChild.left, top)) {
                setStateInternal(STATE_SETTLING)
                ViewCompat.postOnAnimation(
                    releasedChild,
                    SettleRunnable(releasedChild, targetState)
                )
            } else {
                setStateInternal(targetState)
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return constrain(top, if (mHideable) -child.height else mMinOffset, mMaxOffset)
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return child.left
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return if (mHideable) {
                child.height
            } else {
                mMaxOffset - mMinOffset
            }
        }
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomSheetBehavior_Layout
        )
        setPeekHeight(
            a.getDimensionPixelSize(
                R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, 0
            )
        )
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false))
        skipCollapsed = a.getBoolean(
            R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed,
            false
        )
        a.recycle()
        val configuration = ViewConfiguration.get(context)
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity.toFloat()
    }

    private fun dispatchOnSlide(top: Int) {
        val bottomSheet: View? = mViewRef!!.get()
        if (bottomSheet != null && mCallback != null) {
            if (top < mMinOffset) {
                mCallback!!.onSlide(bottomSheet, (top - mMinOffset).toFloat() / mPeekHeight)
            } else {
                mCallback!!.onSlide(
                    bottomSheet,
                    (top - mMinOffset).toFloat() / (mMaxOffset - mMinOffset)
                )
            }
        }
    }

    private inner class SettleRunnable internal constructor(
        private val mView: View,
        @field:State @param:State private val mTargetState: Int
    ) :
        Runnable {
        override fun run() {
            if (mViewDragHelper != null && mViewDragHelper!!.continueSettling(true)) {
                ViewCompat.postOnAnimation(mView, this)
            } else {
                setStateInternal(mTargetState)
            }
        }
    }

    protected class SavedState : AbsSavedState {
        @State
        val state: Int

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            state = source.readInt()
        }

        constructor(superState: Parcelable?, @State state: Int) : super(
            superState!!
        ) {
            this.state = state
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : ClassLoaderCreator<SavedState> {
                override fun createFromParcel(`in`: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(`in`, loader)
                }

                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`, null)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        const val STATE_DRAGGING = 1
        const val STATE_SETTLING = 2
        const val STATE_EXPANDED = 3
        const val STATE_COLLAPSED = 4
        const val STATE_HIDDEN = 5
        private const val HIDE_THRESHOLD = 0.5f
        private const val HIDE_FRICTION = 0.1f

        /**
         * A utility function to get the [TopSheetBehavior] associated with the `view`.
         *
         * @param view The [View] with [TopSheetBehavior].
         * @return The [TopSheetBehavior] associated with the `view`.
         */
        fun <V : View> from(view: V): TopSheetBehavior<V> {
            val params = view.layoutParams
            require(params is CoordinatorLayout.LayoutParams) { "The view is not a child of CoordinatorLayout" }
            val behavior = params.behavior
            require(behavior is TopSheetBehavior<*>) { "The view is not associated with TopSheetBehavior" }
            return behavior as TopSheetBehavior<V>
        }

        fun constrain(amount: Int, low: Int, high: Int): Int {
            return if (amount < low) low else min(amount.toDouble(), high.toDouble()).toInt()
        }
    }
}