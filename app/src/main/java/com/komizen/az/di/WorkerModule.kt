package com.komizen.az.di

import com.komizen.az.worker.SyncWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker { SyncWorker(get(), get(), get()) }
}