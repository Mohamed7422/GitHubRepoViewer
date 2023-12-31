package com.example.githubrepoviewer.data.network

import android.util.Log
import com.example.githubrepoviewer.common.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestCountInterceptor : Interceptor {
    var requestCount = 0
        private set

    override fun intercept(chain: Interceptor.Chain): Response {
        requestCount++
        Log.i("RequestCountInterceptor", "Total API requests made: $requestCount")

        return chain.proceed(chain.request())
    }
}


val okHttpClient = OkHttpClient.Builder()
    .addInterceptor( RequestCountInterceptor())
    .build()

object ApiHelper{




    val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

object GitReposApiClient {

    val gitReposApiClient: GitReposApiService by lazy {
        ApiHelper.retrofit.create(GitReposApiService::class.java)
    }


}

