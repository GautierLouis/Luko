package com.louisgautier.firebase.notification

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.louisgautier.logger.AppLogger
import org.koin.core.context.GlobalContext
import java.util.concurrent.TimeUnit

class FcmTokenUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val token = inputData.getString(KEY_FCM_TOKEN)
            ?: return Result.failure()

        AppLogger.d(tag = "FCM Worker", message = "New token received: $token")

        //Koin might not be initialized - retry until app launch
        return GlobalContext.getOrNull()?.getOrNull<FcmAccessor>()
            ?.registerNewToken(token)
            ?.fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            ) ?: Result.retry()
    }

    companion object {
        const val KEY_FCM_TOKEN = "fcm_token"

        fun enqueue(context: Context, token: String) {
            val data = workDataOf(KEY_FCM_TOKEN to token)

            val request = OneTimeWorkRequestBuilder<FcmTokenUpdateWorker>()
                .setInputData(data)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.Companion.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    "fcm_token_update",
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }
    }
}