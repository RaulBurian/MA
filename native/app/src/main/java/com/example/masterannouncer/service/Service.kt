package com.example.masterannouncer.service

import androidx.lifecycle.LiveData
import androidx.room.Update
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.domain.AnnouncementId
import com.example.masterannouncer.repository.Repository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object Service {

    //Running from the emulator, start the server first.
    private const val URL = "http://10.0.2.2:3000/"
    lateinit var repository: Repository

    interface Service {
        @GET("/announcements")
        suspend fun getAnnouncements(): List<Announcement>

        @POST("/announcements")
        suspend fun postAnnouncement(@Body announcement: Announcement): Announcement

        @DELETE("/announcements")
        suspend fun deleteAnnouncement(@Query("id") id: Long)

        @PUT("/announcements")
        suspend fun updateAnnouncement(@Body announcement: Announcement)
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
    

}