package com.example.panicbutton.api

import com.example.panicbutton.viewmodel.MonitorData
import com.example.panicbutton.viewmodel.RekapData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("nomorRumah") nomorRumah: String,
        @Field("sandi") sandi: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("nomorRumah") nomorRumah: String,
        @Field("sandi") sandi: String
    ): Call<ResponseBody>

    @GET("monitor_new.php")
    fun getMonitoringData(): Call<List<MonitorData>>

    @GET("rekap.php")
    fun getRekapData(): Call<List<RekapData>>
}
