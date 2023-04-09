package uca.baptistearthur.geocaching.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uca.baptistearthur.geocaching.model.Address
import java.lang.reflect.Type


object AddressNetwork {

    private val API_Info = object {
        val base_url = "https://forward-reverse-geocoding.p.rapidapi.com/v1/reverse/"
        val key = "19516a9900mshce10de76f99976bp10f192jsn8c8d82222baa"
    }

    private val rateLimiter = object {
        val maxRequestsPerSecond = 2
        val intervalInMillis = (1000.0 / maxRequestsPerSecond).toLong()
        var lastRequestTime: Long = 0
    }

    val rateLimitInterceptor = Interceptor { chain ->
        val now = System.currentTimeMillis()
        val elapsed = now - rateLimiter.lastRequestTime
        if (elapsed < rateLimiter.intervalInMillis) {
            Thread.sleep(rateLimiter.intervalInMillis - elapsed)
        }
        rateLimiter.lastRequestTime = System.currentTimeMillis()

        chain.proceed(chain.request())
    }
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val gson = GsonBuilder().apply {
        registerTypeAdapter(Address::class.java, AddressDeserializer())
    }.create()

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", API_Info.key)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(interceptor)
        .addInterceptor(rateLimitInterceptor)
        .build()

    val retrofit: AddressAPI by lazy {
        Retrofit.Builder()
            .baseUrl(API_Info.base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AddressAPI::class.java)
    }
}
