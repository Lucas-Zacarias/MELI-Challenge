package com.lucaszacarias.meli.di

import com.lucaszacarias.meli.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }

            defaultRequest {
                url(BuildConfig.BASE_URL)
            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    level = LogLevel.BODY
                    logger = object : io.ktor.client.plugins.logging.Logger {
                        override fun log(message: String) {
                            Timber.tag("HTTP_CLIENT").d(message)
                        }

                    }
                }
            }

        }
    }
}
