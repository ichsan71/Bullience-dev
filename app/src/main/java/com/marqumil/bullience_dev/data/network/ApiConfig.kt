package com.marqumil.bullience_dev.data.network

import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.data.remote.response.LoginResponse
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            Log.d("TOKEN_API", "TRY1")
            val headerInterceptor = Interceptor { chain ->
                val request = chain.request()
                val loginData : LoginResponse? = Hawk.get(SharedPrefs.KEY_LOGIN) as? LoginResponse

                Log.d("TOKEN_API", "TRY2")
                val newRequest = request.newBuilder()
                    .addHeader("token", loginData?.token ?: "")
                    .build()

                Log.d("TOKEN_API", loginData?.token ?: "")

                chain.proceed(newRequest)
            }

            // Create a Gson instance
            val gson = Gson()

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://backend-user-c6rvqi6zjq-et.a.run.app")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiServiceIndobert(): ApiServiceIndobert {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-inference.huggingface.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiServiceIndobert::class.java)
        }
    }
}