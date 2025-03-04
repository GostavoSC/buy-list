package com.gstv.buylist.di

import org.koin.core.context.startKoin

fun doInitKoin() {
    startKoin {
        modules(sharedModule, platformModule())
    }
}