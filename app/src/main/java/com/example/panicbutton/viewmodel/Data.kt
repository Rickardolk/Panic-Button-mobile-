package com.example.panicbutton.viewmodel


data class OnBoardingData(
    val image: Int,
    val title: String,
    val desc: String
)

data class MonitorData(
    val nomor_rumah: String,
    val waktu: String
)

data class LatestMonitor(
    val nomor_rumah: String,
    val waktu: String
)

data class RekapData(
    val id: Int,
    val nomor_rumah: String,
    val waktu: String,
    val status: String
)

data class DetailLog(
    val waktu: String,
    val nomor_rumah: String,
    val status: String
)


