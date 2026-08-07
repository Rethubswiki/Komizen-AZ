package com.komizen.az.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FileUtilsTest {

    @Test
    fun `formatBytes handles bytes`() {
        assertEquals("512.00 B", FileUtils.formatBytes(512))
    }

    @Test
    fun `formatBytes handles kilobytes`() {
        assertEquals("1.00 KB", FileUtils.formatBytes(1024))
    }

    @Test
    fun `formatBytes handles megabytes`() {
        assertEquals("1.00 MB", FileUtils.formatBytes(1024 * 1024))
    }

    @Test
    fun `formatBytes handles gigabytes`() {
        assertEquals("1.50 GB", FileUtils.formatBytes(1024L * 1024 * 1024 * 3 / 2))
    }
}
