package com.lucaszacarias.meli.di

import com.lucaszacarias.meli.data.remote.api.SpaceFlightApi
import com.lucaszacarias.meli.data.remote.api.SpaceFlightApiImpl
import com.lucaszacarias.meli.data.repository.SpaceFlightRepositoryImpl
import com.lucaszacarias.meli.domain.repository.SpaceFlightRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SpaceFlightApi> { SpaceFlightApiImpl(get()) }
    single<SpaceFlightRepository> { SpaceFlightRepositoryImpl(get()) }
}
