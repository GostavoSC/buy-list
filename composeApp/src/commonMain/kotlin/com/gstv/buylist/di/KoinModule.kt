package com.gstv.buylist.di


import com.example.database.AppDatabase
import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.domain.mapper.DatabaseListToListModelMapper
import com.gstv.buylist.domain.mapper.InsertListMapper
import com.gstv.buylist.domain.mapper.ModelMapper
import com.gstv.buylist.domain.use_case.ListsUseCase
import com.gstv.buylist.domain.use_case.RepositoryImpl
import com.gstv.buylist.ui.screens.home.HomeViewModel
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val sharedModule = module {
    single { AppDatabase(get()) }

    single<BuyListRepository> { RepositoryImpl(get()) }
    mapperFactory { DatabaseListToListModelMapper() }
    mapperFactory { InsertListMapper() }
    mapperFactory { DatabaseListToListModelMapper() }

    factory {
        ListsUseCase(
            get(),
            getMapperOf(),
            getMapperOf()
        )
    }
    single { HomeViewModel(get()) }
}

inline fun <reified I, reified O> Module.mapperFactory(
    noinline definition: Definition<ModelMapper<I, O>>
) = factory(
    qualifier = mapperOf<I, O>(),
    definition
)


inline fun <reified I, reified O> mapperOf() =
    named("Mapper:${I::class.qualifiedName}to${O::class.qualifiedName}")

inline fun <reified I, reified O> Scope.getMapperOf(): ModelMapper<I, O> = get(mapperOf<I, O>())