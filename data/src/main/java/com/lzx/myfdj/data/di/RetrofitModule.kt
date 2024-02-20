package com.lzx.myfdj.data.di

import com.lzx.myfdj.data.BuildConfig
import com.lzx.myfdj.data.api.TheSportsDbApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideTimber(): Timber.Tree = Timber.asTree()

    @Provides
    fun provideApiService(retrofit: Retrofit): TheSportsDbApi {
        return retrofit.create(TheSportsDbApi::class.java)
    }

    @Provides
    fun provideLoggingInterceptor(timberTree: Timber.Tree): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message ->
            timberTree.d(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(loggingInterceptor)
        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
}
