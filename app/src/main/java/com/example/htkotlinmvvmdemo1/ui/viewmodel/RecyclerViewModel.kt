package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.City
import com.example.htkotlinmvvmdemo1.bean.response.HotelCity
import com.example.htkotlinmvvmdemo1.bean.response.PostLinkedElong
import com.example.htkotlinmvvmdemo1.network.Api
import com.example.htkotlinmvvmdemo1.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class RecyclerViewModel(application: Application) : AndroidViewModel(application) {
    suspend fun httpGet(): City? {
        return suspendCoroutine {
            Api.api.getSyncCity().enqueue(object : Callback<City> {
                override fun onResponse(
                    call: retrofit2.Call<City>,
                    response: Response<City>
                ) {
                    it.resume(response.body())
                }

                override fun onFailure(call: retrofit2.Call<City>, t: Throwable) {
                    it.resume(null)
                }
            })
        }
    }


    var hotelCityslateResult: MutableStateFlow<NetworkResult<PostLinkedElong>> = MutableStateFlow(NetworkResult.Loading())

    fun HotleCity(city: HotelCity) {
        viewModelScope.launch {
            MenuRepository.HotleCity(city=city).collect {
                hotelCityslateResult.emit(it)
            }
        }
    }


}


