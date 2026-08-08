package com.komizen.az.di

import org.koin.dsl.module

val appModule = module {
    includes(
        databaseModule,
        networkModule,
        repositoryModule,
        viewModelModule,
        workerModule
    )
}