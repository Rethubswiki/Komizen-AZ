package com.komizen.az.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect(
            packageName = "com.komizen.az",
            includeInStartupProfile = true
        ) {
            pressHome()
            startActivityAndWait()
            // Navigate through main screens
            device.findObject(androidx.test.uiautomator.By.desc("Explorar")).click()
            device.waitForIdle()
            device.findObject(androidx.test.uiautomator.By.desc("Instaladas")).click()
            device.waitForIdle()
            device.findObject(androidx.test.uiautomator.By.desc("Ajustes")).click()
            device.waitForIdle()
        }
    }
}
