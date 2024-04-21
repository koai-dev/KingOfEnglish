package com.koai.kingofenglish.ads

import android.app.Activity
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsWorkManager(private val context: Activity, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            return@withContext try {
                AdmobUtils.showAdmob(context)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
}
