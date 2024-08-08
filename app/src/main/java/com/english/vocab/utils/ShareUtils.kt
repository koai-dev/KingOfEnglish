package com.english.vocab.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.applyCanvas
import androidx.core.os.bundleOf
import androidx.core.view.drawToBitmap
import com.english.vocab.BuildConfig
import com.english.vocab.common.ShareView
import com.koai.base.main.action.event.ShareFile
import com.koai.base.main.action.router.BaseRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun ShareView.share(
    context: Context,
    router: BaseRouter?,
) {
    CoroutineScope(Dispatchers.Default).launch {
        try {
            val uri = this@share.drawToBitmap().saveBitmapToCache(context)
            if (uri != null) {
                router?.onShareFile(
                    bundleOf(
                        ShareFile.TITLE to "Share this to Best-friends",
                        ShareFile.EXTRA to uri.toString(),
                        ShareFile.LINK to "https://play.google.com/store/apps/details?id=com.koai.kingofenglish",
                    ),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                val uri =
                    Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).applyCanvas {
                        translate(-scrollX.toFloat(), -scrollY.toFloat())
                        draw(this)
                    }.saveBitmapToCache(context)
                if (uri != null) {
                    router?.onShareFile(
                        bundleOf(
                            ShareFile.TITLE to "Share this to Best-friends",
                            ShareFile.EXTRA to uri.toString(),
                            ShareFile.LINK to "https://play.google.com/store/apps/details?id=com.koai.kingofenglish",
                        ),
                    )
                }
            } catch (e: Exception) {
                router?.onShareFile(
                    bundleOf(
                        ShareFile.TITLE to "Share this to Best-friends",
                        ShareFile.LINK to "https://play.google.com/store/apps/details?id=com.koai.kingofenglish",
                    ),
                )
            }
        }
    }
}

fun Bitmap.saveBitmapToCache(context: Context): Uri? {
    return try {
        val cacheDir: File = context.cacheDir
        Log.d("Cache Directory", cacheDir.absolutePath)
        val imageFile = File(cacheDir, "my_koe.png")
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
