package com.koai.kingofenglish.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import com.koai.kingofenglish.BuildConfig
import com.koai.base.utils.ScreenUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun View.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(this.context), ScreenUtils.getScreenWidth(this.context)*9/20, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}

fun Bitmap.saveBitmapToCache(context: Context): Uri?{
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