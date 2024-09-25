package com.example.panicbutton.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateMapOf
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class ViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.create()
    private val _panicButtonData = MutableLiveData<List<PanicButtonData>>()
    private val _latestMonitor = MutableLiveData<List<PanicButtonData>>()

    val panicButtonData: LiveData<List<PanicButtonData>> = _panicButtonData
    val latestMonitor: LiveData<List<PanicButtonData>> = _latestMonitor
    val rekapData = MutableLiveData<List<PanicButtonData>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    // function utk registrasi
    fun register(nama: String, nomorRumah: String, sandi: String, context: Context, navController: NavController) {
        if (nama.isBlank() || nomorRumah.isBlank() || sandi.isBlank()){
            Toast.makeText(context, "Nama, Nomor Rumah, dan Sandi harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        val call = apiService.registerService(nama, nomorRumah, sandi)

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
        val call = apiService.loginService(nomorRumah, sandi)
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
                    val jsonObject = JSONObject(jsonResponse)
                    val userName = jsonObject.getString("nama")
                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                    saveUserLogin(context, nomorRumah, userName)
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

    private fun saveUserLogin(context: Context, nomorRumah: String, namaUser: String) {
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nomorRumah", nomorRumah)
            putString("namaUser", namaUser)
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
        val call = apiService.monitorService()

        call.enqueue(object : Callback<List<PanicButtonData>> {
            override fun onResponse(call: Call<List<PanicButtonData>>, response: Response<List<PanicButtonData>>) {
                if (response.isSuccessful) {
                    _panicButtonData.postValue(response.body())
                } else {
                    Log.e("MonitoringError", "Error: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                Log.e("MonitoringError", "Failed to fetch data: ${t.localizedMessage}")
            }
        })
    }

    fun monitorLatest() {
        val call = apiService.latestMonitorService()

        call.enqueue(object : Callback<List<PanicButtonData>> {
            override fun onResponse(call: Call<List<PanicButtonData>>, response: Response<List<PanicButtonData>>) {
                if (response.isSuccessful) {
                    _latestMonitor.postValue(response.body())
                } else {
                    Log.e("MonitoringError", "Error: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                Log.e("MonitoringError", "Failed to fetch data: ${t.localizedMessage}")
            }
        })
    }

    // fun data rekap
    fun fetchRekapData() {
        isLoading.value = true
        val call = apiService.rekapService()

        call.enqueue(object : Callback<List<PanicButtonData>> {
            override fun onResponse(
                call: Call<List<PanicButtonData>>,
                response: Response<List<PanicButtonData>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("DataRekap", "Response: ${response.body()}")
                    Log.d("DataRekap", "Raw response: ${response.raw()}")
                    rekapData.value = response.body()
                } else {
                    errorMessage.value = "GAgal mendapatkan data"
                }
            }
            override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                Log.e("DataRekap", "gagal; mengambil data", t)
                isLoading.value = false
                errorMessage.value = t.message
            }
        })
    }

    //function utk detailLog
    fun detailLog(nomorRumah: String) {
        val call = apiService.detailLogService(nomorRumah)

        call.enqueue(object : Callback<List<PanicButtonData>>{
            override fun onResponse(
                call: Call<List<PanicButtonData>>,
                response: Response<List<PanicButtonData>>
            ) {
                if (response.isSuccessful) {
                    _panicButtonData.value = response.body()
                } else {
                    _panicButtonData.value = emptyList()
                }
            }
            override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                _panicButtonData.value = emptyList()
            }
        })
    }

    //fun utk latest rekap
    fun latestRekap(){
        apiService.rekapService().enqueue(object : Callback<List<PanicButtonData>> {
            override fun onResponse(
                call: Call<List<PanicButtonData>>,
                response: Response<List<PanicButtonData>>
            ) {
                if (response.isSuccessful) {
                    rekapData.value = response.body()?.take(6)
                } else {
                    errorMessage.value = "Error retrieving data"
                }
            }
            override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                errorMessage.value = t.message
            }
        })
    }
}



//class Panic Button
class PanicButton (application: Application) : AndroidViewModel(application) {
    private val client = OkHttpClient()
    private val sharedPreferences = application.getSharedPreferences("log_preferences", Context.MODE_PRIVATE)
    private val apiService: ApiService = RetrofitClient.create()
    private val _detailLogData = MutableLiveData<List<PanicButtonData>>()
    private val _statusMap = mutableStateMapOf<Int, Boolean>()
    private val _panicButtonData = MutableLiveData<List<PanicButtonData>>()

    fun toggleDevice(
        on: Boolean,
        nomorRumah: String,
        pesan: String,
        prioritas: String,
        snackbarHostState: SnackbarHostState,
        onLoadingChange: (Boolean) -> Unit
    ) {
        val ipAddress = getApplication<Application>().getString(R.string.ipAdd)
        val state = if (on) 1 else 0
        val url = "http://$ipAddress/button/esp_iot/proses.php?id=2&state=$state&nomor_rumah=$nomorRumah&pesan=$pesan&prioritas=$prioritas"

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

    fun simpanLogStatus(id: Int, status: Boolean) {
        sharedPreferences.edit().putBoolean("log_$id", status).apply()
    }
    fun ambilLogStatus(id: Int): Boolean {
        return sharedPreferences.getBoolean("log_$id", false)
    }

    fun updateLogStatus(id: Int, status: String) {
        Log.d("UpdateStatus", "Mengirim ID: $id, Status: $status")
        val call = apiService.updateStatusService(id, status)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        val responseString = responseBody.string()
                        Log.d("UpdateStatus", "Respons dari server: $responseString")

                        if (responseString.contains("\"success\":true")) {
                            _statusMap[id] = true
                            _detailLogData.value = _detailLogData.value?.map {
                                if (it.id == id) {
                                    it.copy(status = status)
                                } else it
                            }
                            simpanLogStatus(id, true)
                            Log.d("UpdateStatus", "Status berhasil diupdate")
                            _panicButtonData.value = _panicButtonData.value?.map {
                                if (it.id == id) {
                                    it.copy(status = "selesai")
                                } else it
                            }
                        } else {
                            Log.e("UpdateStatus", "Update gagal, server merespon: $responseString")
                        }
                    } ?: run {
                        Log.e("UpdateStatus", "Response body kosong")
                    }
                } else {
                    Log.e("UpdateStatus", "Gagal mengupdate status: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("UpdateStatus", "Error saat mengupdate status: ${t.message}")
            }
        })
    }
    fun isLogCompleted(id: Int): Boolean {
        return ambilLogStatus(id)
    }
}




