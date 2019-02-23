package com.mvvm.dagger.sample.di

import android.content.Context
import com.mvvm.dagger.sample.BuildConfig
import com.mvvm.dagger.sample.data.preference.PreferenceManager
import com.mvvm.dagger.sample.livedata.LiveDataCallAdapterFactory
import com.mvvm.dagger.sample.webservice.IApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {

    enum class AuthenticationType {
        BASIC, OAUTH, NONE
    }

    companion object {
        private const val CONNECT_TIMEOUT = 60L
        private const val READ_TIMEOUT = 60L
        private const val WRITE_TIMEOUT = 60L
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient =
            OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                        for (entry in getHeaders(context, AuthenticationType.NONE).entries) {
                            requestBuilder.addHeader(entry.key, entry.value)
                        }
                        requestBuilder.method(original.method(), original.body())
                        chain.proceed(requestBuilder.build())
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                    .client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): IApi = retrofit.create(IApi::class.java)

    private fun getHeaders(context: Context, authType: AuthenticationType): Map<String, String> {
        val requestHeader = hashMapOf(
                "version" to BuildConfig.VERSION_NAME
        )
        val accessToken = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN, "")
        when (authType) {
            AuthenticationType.BASIC -> {
                requestHeader["Authorization"] = "Basic $accessToken"
            }
            AuthenticationType.OAUTH -> {
                requestHeader["Authorization"] = "Bearer $accessToken"
            }
            AuthenticationType.NONE -> Unit
        }
        return requestHeader
    }


}