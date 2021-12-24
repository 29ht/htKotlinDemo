package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.caspar.base.base.BaseViewModel
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.Hotel
import com.example.htkotlinmvvmdemo1.bean.response.requestHotel
import com.example.htkotlinmvvmdemo1.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
 *  "ht" 创建 2021/12/8.
 *   界面名称以及功能: 新闻列表
 */
class HotelViewModel(application: Application) : BaseViewModel(application) {

    /**
     *   网络请求方法**********************************************************************
     */
    var hotel: MutableStateFlow<NetworkResult<Hotel>> = MutableStateFlow(NetworkResult.Loading())
    // 腾讯新闻
    fun hotelListModel(result: requestHotel) {
        viewModelScope.launch {
            MenuRepository.getHotelList(result).collect {
                hotel.emit(it)
            }
        }
    }

    /*
     *其他方法类**********************************************************************
     */

}
