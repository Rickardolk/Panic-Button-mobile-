package com.example.panicbutton.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import android.util.Base64
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse


class ViewModel : ViewModel() {
    private val client = OkHttpClient()
    private val apiService: ApiService = RetrofitClient.create()
    private val _panicButtonData = MutableLiveData<List<PanicButtonData>>()
    private val _latestMonitor = MutableLiveData<List<PanicButtonData>>()
    private val _status = MutableLiveData<Boolean>()

    val panicButtonData: LiveData<List<PanicButtonData>> = _panicButtonData
    val latestMonitor: LiveData<List<PanicButtonData>> = _latestMonitor
    val rekapData = MutableLiveData<List<PanicButtonData>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val keterangan = MutableLiveData<String>()
    val getRekapData = MutableLiveData<List<GetDetailRekap>>()


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
                Log.d("API Response Code", response.code().toString())
                val jsonResponse = response.body()?.string()
                Log.d("API Response", jsonResponse ?: "No response")
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

    // fun utk toggle device
    fun toggleDevice(context: Context, on: Boolean, nomorRumah: String, pesan: String, prioritas: String) {
        val ipAddress =  context.getString(R.string.ipAdd)
        val state = if (on) 1 else 0
        val url = "http://$ipAddress/button/esp_iot/proses.php?id=2&state=$state&nomor_rumah=$nomorRumah&pesan=$pesan&prioritas=$prioritas"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                Log.e("ToggleDevice", "Request Failed: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("ToggleDevice", "Response: $responseData")
                } else {
                    Log.e("ToggleDevice", "Request Failed: ${response.message}")
                }
            }
        })
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
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.rekapService().awaitResponse()
                }
                if (response.isSuccessful) {
                    _panicButtonData.postValue(response.body())
                    rekapData.value = response.body()
                } else {
                    Log.e("FetchRekapData", "error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FetchRekapData", "gagal mengambil DAta", e)
            } finally {
                isLoading.value = false
            }
        }
    }

    //function utk detailLog
    fun detailLog(nomorRumah: String) {
        viewModelScope.launch {
            try {
                isLoading.value =true
                val response = withContext(Dispatchers.IO) {
                    apiService.detailLogService(nomorRumah).awaitResponse()
                }
                if (response.isSuccessful) {
                    _panicButtonData.value = response.body()
                } else {
                    Log.e("detailLog", "error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("detailLog", "gagal mengambil DAta", e)
            }
            finally {
                isLoading.value = false
            }
        }
    }

    fun getDetailRekap(nomorRumah: String){
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.getDetailRekap(nomorRumah).awaitResponse()
                }
                if (response.isSuccessful){
                    val rekap = response.body()
                    getRekapData.postValue(listOf(rekap!!))
                } else {
                    errorMessage.postValue("Gagal ambil data")
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            } finally {
                isLoading.value = false
            }
        }
    }

    //fun utk latest rekap
    fun latestRekap(){
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.rekapService().execute()
                }
                if (response.isSuccessful) {
                    rekapData.value = response.body()?.take(6)
                } else {
                    Log.e("latestRekap", "error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            } finally {
                isLoading.value = false
            }
        }
    }

    //fun UserHistory
    fun userHIstory(context: Context){
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val nomorRumah = sharedPref.getString("nomorRumah", null)

        nomorRumah?.let {
            val call = apiService.detailLogService(it)
            call.enqueue(object : Callback<List<PanicButtonData>>{
                override fun onResponse(
                    call: Call<List<PanicButtonData>>,
                    response: Response<List<PanicButtonData>>
                ) {
                    if (response.isSuccessful) {
                        rekapData.value = response.body()
                    } else {
                        rekapData.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<List<PanicButtonData>>, t: Throwable) {
                    rekapData.value = emptyList()
                }
            })
        }
    }

    // function utk upload image ke database
    fun uploadImageToDatabase(context: Context, imageUri: Uri, uploadType: String, onSuccess: (String) -> Unit) {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)

        if (inputStream != null) {
            val imageBytes = inputStream.readBytes()
            val encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
            val nomorRumah = sharedPref.getString("nomorRumah", null)

            if (nomorRumah != null) {
                val call = when (uploadType) {
                    "profile" -> apiService.uploadProfileImage(nomorRumah, encodedImage)
                    "cover" -> apiService.uploadCoverImage(nomorRumah, encodedImage)
                    else -> null
                }

                call?.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val newImagePath = response.body()?.string()
                            if (newImagePath != null) {
                                Toast.makeText(context, "Gambar berhasil di upload", Toast.LENGTH_SHORT).show()
                                onSuccess(newImagePath)
                            } else {
                                Toast.makeText(context,"Gagal", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else {
            Toast.makeText(context, "Gagal membaca gambar", Toast.LENGTH_SHORT).show()
        }
    }

    // fun utk get profile image
    fun getProfileImage(nomorRumah: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.getProfileImage(nomorRumah).execute()
                }
                if (response.isSuccessful) {
                    onResult(response.body()?.image_profile)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            } finally {
                isLoading.value = false
            }
        }
    }

    //fun utk get cover image
    fun getCoverImage(nomorRumah: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.getCoverImage(nomorRumah).execute()
                }
                if (response.isSuccessful) {
                    onResult(response.body()?.image_cover)
                } else {
                    onResult(null)
                }
            } catch (e: Exception){
                errorMessage.postValue(e.message)
            } finally {
                isLoading.value = false
            }
        }
    }

    //fun utk upload keterangan ke database
    fun updateKeterangan(nomorRumah: String, keterangan: String) {
        val request = UpdateKeteranganRequest(nomorRumah, keterangan)

        val call = RetrofitClient.create().updateKeterangan(request)
        call.enqueue(object : Callback<UpdateKeteranganResponse> {
            override fun onResponse(
                call: Call<UpdateKeteranganResponse>,
                response: Response<UpdateKeteranganResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("UpdateKeterangan", "Response: ${response.body()}")
                } else {
                    Log.d("UpdateKeterangan", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UpdateKeteranganResponse>, t: Throwable) {
                Log.d("UpdateKeterangan", "Failure: ${t.message}")
            }
        })
    }

    // fun menampilkan keteragan user
    fun getKeteranganUser(nomorRumah: String) {
        val call = apiService.getKeteranganUser(nomorRumah)
        call.enqueue(object : Callback<KeteranganResponse> {
            override fun onResponse(call: Call<KeteranganResponse>, response: Response<KeteranganResponse>) {
                if (response.isSuccessful) {
                    val keteranganResponse = response.body()
                    if (keteranganResponse != null && keteranganResponse.status == "success") {
                        keterangan.value = keteranganResponse.keterangan
                    } else {
                        errorMessage.value = "Data tidak ditemukan"
                    }
                } else {
                    errorMessage.value = "Gagal mengambil data"
                }
            }
            override fun onFailure(call: Call<KeteranganResponse>, t: Throwable) {
                errorMessage.value = "Error: ${t.message}"
            }
        })
    }

    // update status proses/selesai
    fun updateLogStatus(id: Int, status: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    apiService.updateStatusService(id, status).execute()
                }
                if (response.isSuccessful){
                    _status.postValue(true)
                } else {
                    Log.d("Update status", "gagal")
                    _status.postValue(false)
                }
            } catch (e: Exception) {
                Log.d("Update status", "Exception: ${e.message}")
                _status.postValue(false)
            } finally {
                isLoading.value = false
            }
        }
    }
}





