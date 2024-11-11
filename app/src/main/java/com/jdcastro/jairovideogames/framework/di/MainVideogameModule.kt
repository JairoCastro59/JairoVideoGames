package com.jdcastro.jairovideogames.framework.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jdcastro.jairovideogames.BuildConfig
import com.jdcastro.jairovideogames.framework.data.config.VideogameApiService
import com.jdcastro.jairovideogames.framework.data.implementation.VideogameDataSourceImpl
import com.jdcastro.jairovideogames.ui.dashboard.constants.DashboardConstants
import com.jdcastro.jairovideogames.data.dataSource.VideogameDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainVideogameModule {

    @Provides
    fun providesBaseUrl() = DashboardConstants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(VideogameApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(videogameDataSourceImp: VideogameDataSourceImpl): VideogameDataSource =
        videogameDataSourceImp
}