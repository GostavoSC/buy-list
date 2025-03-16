package com.gstv.buylist.di


import com.example.database.AppDatabase
import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.domain.mapper.DatabaseListsToListsModelMapper
import com.gstv.buylist.domain.mapper.InsertListMapper
import com.gstv.buylist.domain.mapper.ItemsToItemMapper
import com.gstv.buylist.domain.mapper.ListToModelMapper
import com.gstv.buylist.domain.mapper.ModelMapper
import com.gstv.buylist.domain.use_case.ItemsUseCase
import com.gstv.buylist.domain.use_case.ListsUseCase
import com.gstv.buylist.domain.use_case.RepositoryImpl
import com.gstv.buylist.ui.screens.creation.NewListViewModel
import com.gstv.buylist.ui.screens.history.HistoryViewModel
import com.gstv.buylist.ui.screens.home.HomeViewModel
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val sharedModule = module {
    single { AppDatabase(get()) }

    single<BuyListRepository> { RepositoryImpl(get()) }

    singleOf(::DatabaseListsToListsModelMapper)
    singleOf(::InsertListMapper)
    singleOf(::ListToModelMapper)
    singleOf(::ItemsToItemMapper)

    factory {
        ListsUseCase(
            get(),
            listsMapper = get<DatabaseListsToListsModelMapper>(),
            listMapper = get<ListToModelMapper>(),
            insertListMapper = get<InsertListMapper>()
        )
    }

    factory {
        ItemsUseCase(
            get(),
            itemMapper = get<ItemsToItemMapper>()
        )
    }

    singleOf(::HomeViewModel)
    singleOf(::HistoryViewModel)
    factoryOf(::NewListViewModel)

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