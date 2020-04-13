package com.worldofplay.core.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.worldofplay.core.BuildConfig
import com.worldofplay.core.qualifiers.OkhttpClient
import com.worldofplay.core.qualifiers.RetrofitStories
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Dagger module to provide core data functionality.
 */
@Module
open class CoreDataModule {
    private val READ_TIMEOUT = 30L
    private val CONNECT_TIMEOUT = 15L

    @Provides
    @RetrofitStories
    fun retrofitStories(@OkhttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @OkhttpClient
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(stethoInterceptor)
            .build()

    @Provides
    fun provideStethoInterceptor() = StethoInterceptor()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
