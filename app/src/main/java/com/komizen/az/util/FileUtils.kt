package com.komizen.az.util

import android.content.Context
import java.io.File

object FileUtils {

    fun getCacheSize(context: Context): String {
        val cacheDir = context.cacheDir
        val size = calculateSize(cacheDir)
        return formatBytes(size)
    }

    fun clearCache(context: Context): Boolean {
        return try {
            context.cacheDir.deleteRecursively()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun calculateSize(dir: File): Long {
        var size = 0L
        dir.listFiles()?.forEach { file ->
            size += if (file.isDirectory) calculateSize(file) else file.length()
        }
        return size
    }

    fun formatBytes(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var size = bytes.toDouble()
        var unitIndex = 0
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        return "%.2f %s".format(size, units[unitIndex])
    }
}
