package com.komizen.az.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.komizen.az.data.preferences.UserPreferences
import com.komizen.az.domain.usecase.RefreshExtensionsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val refreshUseCase: RefreshExtensionsUseCase
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val preferences: UserPreferences by inject()

    override suspend fun doWork(): Result {
        return try {
            setProgress(workDataOf(PROGRESS to 0))

            val result = refreshUseCase()

            setProgress(workDataOf(PROGRESS to 100))

            if (result.isSuccess) {
                preferences.updateLastSyncTime(System.currentTimeMillis())
                Result.success(workDataOf(SYNC_RESULT to "Sync completed"))
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            if (runAttemptCount < MAX_RETRIES) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val WORK_NAME = "komizen_sync_work"
        const val PROGRESS = "PROGRESS"
        const val SYNC_RESULT = "SYNC_RESULT"
        const val MAX_RETRIES = 3
    }
}