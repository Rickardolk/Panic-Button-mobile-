package com.example.panicbutton.viewmodel


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
    var isCompleted: Boolean = false
)



