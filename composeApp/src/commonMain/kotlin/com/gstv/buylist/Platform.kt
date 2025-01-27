package com.gstv.buylist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform