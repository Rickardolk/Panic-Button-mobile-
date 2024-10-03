package com.example.panicbutton.viewmodel

import com.google.gson.annotations.SerializedName


data class OnBoardingData(
    val image: Int,
    val title: String,
    val desc: String
)

data class PanicButtonData(
    val id: Int,
    val nomor_rumah: String,
    val waktu: String,
    val pesan: String,
    val prioritas: String,
    val status : String,
    val image_profile: String?,
    val image_cover: String?,
    var isCompleted: Boolean = false
)

data class UpdateKeteranganRequest(
    val nomorRumah: String,
    val keterangan: String
)

data class UpdateKeteranganResponse(
    val status: Boolean,
    val message: String
)

data class KeteranganResponse(
    val status: String,
    val nomorRumah: String,
    val keterangan: String
)

data class GetDetailRekap(
    @SerializedName("nama") val nama: String?,
    @SerializedName("norum") val nomorRumah: String?,
    @SerializedName("image_profile") val imageProfile: String?,
    @SerializedName("image_cover") val imageCover: String?,
    @SerializedName("keterangan_user") val keteranganUser: String?
)







