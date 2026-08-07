package com.komizen.az.data.repository

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class ExtensionInstaller(private val context: Context) {

    suspend fun install(apkUrl: String, pkg: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val request = DownloadManager.Request(Uri.parse(apkUrl)).apply {
                setTitle("Komizen Extension")
                setDescription("Downloading $pkg")
                setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "$pkg.apk")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = dm.enqueue(request)

            // Poll for completion
            var finished = false
            while (!finished) {
                delay(500)
                val cursor = dm.query(android.app.DownloadManager.Query().setFilterById(downloadId))
                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(android.app.DownloadManager.COLUMN_STATUS))
                    if (status == android.app.DownloadManager.STATUS_SUCCESSFUL) {
                        finished = true
                        val uri = dm.getUriForDownloadedFile(downloadId)
                        uri?.let { installApk(it) }
                    } else if (status == android.app.DownloadManager.STATUS_FAILED) {
                        cursor.close()
                        return@withContext Result.failure(Exception("Download failed"))
                    }
                }
                cursor.close()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun installApk(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }

    fun uninstall(pkg: String) {
        val intent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:$pkg")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
