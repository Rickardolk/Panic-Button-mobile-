package com.example.panicbutton.api

import com.example.panicbutton.viewmodel.PanicButtonData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register.php")
    fun registerService(
        @Field("nama") nama: String,
        @Field("nomorRumah") nomorRumah: String,
        @Field("sandi") sandi: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login.php")
    fun loginService(
        @Field("nomorRumah") nomorRumah: String,
        @Field("sandi") sandi: String
    ): Call<ResponseBody>

    @GET("monitor.php")
    fun monitorService(): Call<List<PanicButtonData>>

    @GET("latest_monitor.php")
    fun latestMonitorService(): Call<List<PanicButtonData>>

    @GET("rekap.php")
    fun rekapService(): Call<List<PanicButtonData>>

    @GET("detail_log.php")
    fun detailLogService(
        @Query("nomor_rumah") nomorRumah: String
    ): Call<List<PanicButtonData>>

    @FormUrlEncoded
    @POST("update_status.php")
    fun updateStatusService(
        @Field("id") id: Int,
        @Field("status") status: String
    ): Call<ResponseBody>
}
