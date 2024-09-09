package com.example.panicbutton.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.api.ApiService
import com.example.panicbutton.api.RetrofitClient
import com.example.panicbutton.api.RetrofitClientWeb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class ViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.create()
    private val apiServiceWeb: ApiService = RetrofitClientWeb.create()
    private val _monitoringData = MutableLiveData<List<MonitorData>>()

    val monitoringData: LiveData<List<MonitorData>> = _monitoringData
    val rekapData = MutableLiveData<List<RekapData>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    // function utk registrasi
    fun register(nomorRumah: String, sandi: String, context: Context, navController: NavController) {
        if (nomorRumah.isBlank() || sandi.isBlank()){
            Toast.makeText(context, "Nomor Rumah dan Sandi harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        val call = apiService.registerUser(nomorRumah, sandi)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()?.string()
                    if (jsonResponse?.contains("success") == true) {
                        Toast.makeText(context,"Registrasi berhasil", Toast.LENGTH_SHORT).show()
                        navController.navigate("login")
                    } else {
                        Toast.makeText(context, "Nomor rumah sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //function utk login
    @SuppressLint("SuspiciousIndentation")
    fun login(nomorRumah: String, sandi: String, context: Context, navController: NavController) {
        if (nomorRumah.isBlank() || sandi.isBlank()) {
            Toast.makeText(context, "MAsuk gagal: Lengkapi data di atas", Toast.LENGTH_SHORT).show()
            return
        }
        val call = apiService.loginUser(nomorRumah, sandi)
        val admin_norum = "admin"
        val admin_sandi = "admin"

            if (nomorRumah == admin_norum && sandi == admin_sandi) {
                Toast.makeText(context, "Login sebagai admin berhasil!", Toast.LENGTH_SHORT).show()
                navController.navigate("admin") {
                    popUpTo("login") { inclusive = true }
                }
                return
            }

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val jsonResponse = response.body()?.string()
                if (jsonResponse?.contains("success") == true) {
                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                    saveUserLogin(context, nomorRumah)
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Nomor rumah atau sandi salah", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserLogin(context: Context, nomorRumah: String) {
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nomorRumah", nomorRumah)
            apply()
        }
    }

    fun checkUserLogin(context: Context, navController: NavController) {
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val nomorRumah = sharedPref.getString("nomorRumah", null)

        if (nomorRumah != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    //fun utk logout
    fun logout(context: Context, navController: NavController) {
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("nomorRumah")
            apply()
        }

        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }

    // function utk monitoring
    fun monitoring() {
        val call = apiServiceWeb.getMonitoringData()

        call.enqueue(object : Callback<List<MonitorData>> {
            override fun onResponse(call: Call<List<MonitorData>>, response: Response<List<MonitorData>>) {
                if (response.isSuccessful) {
                    _monitoringData.postValue(response.body())
                } else {
                    Log.e("MonitoringError", "Error: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<MonitorData>>, t: Throwable) {
                Log.e("MonitoringError", "Failed to fetch data: ${t.localizedMessage}")
            }
        })
    }

    // fun data rekap
    fun fetchRekapData() {
        isLoading.value = true
        val call = apiServiceWeb.getRekapData()

        call.enqueue(object : Callback<List<RekapData>> {
            override fun onResponse(
                call: Call<List<RekapData>>,
                response: Response<List<RekapData>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    rekapData.value = response.body()
                } else {
                    errorMessage.value = "GAgal mendapatkan data"
                }
            }
            override fun onFailure(call: Call<List<RekapData>>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = t.message
            }
        })
    }
}



//class Panic Button
class PanicButton (application: Application) : AndroidViewModel(application) {
    private val client = OkHttpClient()

    fun toggleDevice(
        on: Boolean,
        nomorRumah: String,
        snackbarHostState: SnackbarHostState,
        onLoadingChange: (Boolean) -> Unit
    ) {
        val ipAddress = getApplication<Application>().getString(R.string.ipAdd)
        val state = if (on) 1 else 0
        val url = "http://$ipAddress/button/esp_iot/proses.php?id=2&state=$state&nomor_rumah=$nomorRumah"

        val request = Request.Builder()
            .url(url)
            .build()

        onLoadingChange(true)

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                Log.e("ToggleDevice", "Request Failed: ${e.message}")
                CoroutineScope(Dispatchers.Main).launch {
                    snackbarHostState.showSnackbar("Error: ${e.message}")
                    onLoadingChange(false)

                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        val responseData = response.body?.string()
                        Log.d("ToggleDevice", "Response: $responseData")
                        snackbarHostState.showSnackbar("Device toggled successfully")
                    } else {
                        Log.e("ToggleDevice", "Request Failed: ${response.message}")
                        snackbarHostState.showSnackbar("Failed to toggle device: ${response.message}")
                    }
                    onLoadingChange(false)

                }
            }
        })
    }
}




