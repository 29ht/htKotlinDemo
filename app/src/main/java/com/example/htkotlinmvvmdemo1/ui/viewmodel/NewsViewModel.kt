package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.caspar.base.base.BaseViewModel
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.News
import com.example.htkotlinmvvmdemo1.bean.response.NewsResult
import com.example.htkotlinmvvmdemo1.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
 *  "ht" 创建 2021/12/8.
 *   界面名称以及功能: 新闻列表
 */
class NewsViewModel(application: Application) : BaseViewModel(application) {


    private var wyListResult = ArrayList<NewsResult>()
    private var txListResult = ArrayList<NewsResult>()
    var news: MutableStateFlow<NetworkResult<News>> = MutableStateFlow(NetworkResult.Loading())
    private var wyPage = 0
    private var txPage = 0

    //网络请求方法**********************************************************************
    // 网易新闻
    fun wyiNewsModel() {
        wyUpdate()//刷新wyPage（页数）
        viewModelScope.launch {
            MenuRepository.getWangyiNews(10, wyPage).collect {
                news.emit(it)
            }
        }
    }

    // 腾讯新闻
    fun txNewsModel() {
        txUpdate()//刷新txPage（页数）
        viewModelScope.launch {
            MenuRepository.getTengxunNews(txPage).collect {
                news.emit(it)
            }
        }
    }

    /*
     *其他方法类**********************************************************************
     */
    // 分页（网易新闻）
    fun wyAddAll(NsList: ArrayList<NewsResult>?): ArrayList<NewsResult> {
        if (NsList != null) {
            for (nc in NsList) {
                wyListResult.add(nc)
            }
        }
        return wyListResult
    }
    //分页（腾讯新闻）
    fun txAddAll(NsList: ArrayList<NewsResult>?): ArrayList<NewsResult> {
        if (NsList != null) {
            for (nc in NsList) {
                txListResult.add(nc)
            }
        }
        return txListResult
    }

    //获取腾讯新闻全部请求的数据
    fun txAll(): ArrayList<NewsResult> {
        return txListResult
    }

    //获取网易新闻全部请求的数据
    fun wyAll(): ArrayList<NewsResult> {
        return wyListResult
    }

    //更新网易页数请求参数
    private fun wyUpdate() {
        wyPage += 1
    }

    //更新腾讯页数请求参数
    private fun txUpdate() {
        txPage += 1
    }
}
