package com.mvaresedev.swapp.di

import com.mvaresedev.swapp.BuildConfig
import com.mvaresedev.swapp.data.db.StarWarsDatabase
import com.mvaresedev.swapp.data.db.mapper.DbMapperImpl
import com.mvaresedev.swapp.data.network.StarWarsApi
import com.mvaresedev.swapp.data.network.mapper.NetworkMapperImpl
import com.mvaresedev.swapp.data.repo.StarWarsRepositoryImpl
import com.mvaresedev.swapp.domain.mapper.DbMapper
import com.mvaresedev.swapp.domain.mapper.NetworkMapper
import com.mvaresedev.swapp.domain.repo.StarWarsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://swapi.dev/api/"
private const val CLIENT_TIMEOUT = 30L

val appModule = module {

    single {
        val clientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        clientBuilder.build()
    }


    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(StarWarsApi::class.java)
    }

    single { StarWarsDatabase.create(androidContext()) }

    single<DbMapper> { DbMapperImpl() }

    single<NetworkMapper> { NetworkMapperImpl() }

    single<StarWarsRepository> {
        StarWarsRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }
}