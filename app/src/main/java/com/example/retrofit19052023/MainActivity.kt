package com.example.retrofit19052023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okhttp = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listPosts = async { apiService.getPost() }.await()
                listPosts.forEach { Log.d("BBB", it.toString()) }
            } catch (e: Exception) {

            }
        }
    }
}