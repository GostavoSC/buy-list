package com.gstv.buylist.di

import app.cash.sqldelight.db.SqlDriver
import com.gstv.buylist.DatabaseDriverFactory
import org.koin.dsl.module

fun platformModule() = module {
    single { DatabaseDriverFactory() }
    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
}