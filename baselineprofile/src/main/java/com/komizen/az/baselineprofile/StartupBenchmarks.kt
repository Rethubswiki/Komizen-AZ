package com.komizen.az.baselineprofile

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCold() = benchmark(StartupMode.COLD)

    @Test
    fun startupWarm() = benchmark(StartupMode.WARM)

    @Test
    fun startupHot() = benchmark(StartupMode.HOT)

    private fun benchmark(startupMode: StartupMode) {
        rule.measureRepeated(
            packageName = "com.komizen.az",
            metrics = listOf(StartupTimingMetric()),
            startupMode = startupMode,
            iterations = 5,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
        }
    }
}
