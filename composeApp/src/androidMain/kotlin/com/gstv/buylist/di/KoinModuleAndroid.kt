package com.gstv.buylist.di

import app.cash.sqldelight.db.SqlDriver
import com.gstv.buylist.DatabaseDriverFactory
import org.koin.dsl.module

val platformModule = module {

    single { DatabaseDriverFactory(context = get()) }

    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
}
