package com.gstv.buylist.domain.mapper

interface ModelMapper<I, O> {
    operator fun invoke(from: I): O
}