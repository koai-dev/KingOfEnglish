package com.koai.kingofenglish.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.View.MeasureSpec
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.view.drawToBitmap
import com.koai.base.main.action.event.ShareFile
import com.koai.base.main.action.router.BaseRouter
import com.koai.kingofenglish.BuildConfig
import com.koai.base.utils.ScreenUtils
import com.koai.kingofenglish.common.ShareView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun ShareView.share(context: Context, router: BaseRouter?) {
    CoroutineScope(Dispatchers.IO).launch {
        val uri = this@share.drawToBitmap().saveBitmapToCache(context)
        if (uri != null) {
            router?.onShareFile(
                bundleOf(
                    ShareFile.TITLE to "Share this to Best-friends",
                    ShareFile.EXTRA to uri.toString(),
                    ShareFile.LINK to "https://play.google.com/store/apps/details?id=com.koai.kingofenglish"
                )
            )
        }
    }
}

fun Bitmap.saveBitmapToCache(context: Context): Uri? {
    return try {
        val cacheDir: File = context.cacheDir
        val imageFile = File(cacheDir, "my_image.jpg")
        val fos = FileOutputStream(imageFile)
        this.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        fos.flush()
        fos.close()
        FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", imageFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}