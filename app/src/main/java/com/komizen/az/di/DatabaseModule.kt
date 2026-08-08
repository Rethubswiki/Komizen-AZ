package com.komizen.az.di

import androidx.room.Room
import com.komizen.az.data.local.AppDatabase
import com.komizen.az.data.local.ExtensionDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ExtensionDao> { get<AppDatabase>().extensionDao() }
}