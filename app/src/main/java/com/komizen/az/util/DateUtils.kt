package com.komizen.az.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun formatTimestamp(timestamp: Long, pattern: String = "dd/MM/yyyy HH:mm"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun getCurrentTimestamp(): Long = System.currentTimeMillis()

    fun isOlderThan(timestamp: Long, hours: Long): Boolean {
        val diff = System.currentTimeMillis() - timestamp
        return diff > hours * 60 * 60 * 1000
    }
}