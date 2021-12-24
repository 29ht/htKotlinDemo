package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import com.caspar.base.base.BaseViewModel

/**
 *  "ht" 创建 2021/12/1
 *   界面名称以及功能: 首页功能菜单
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {
    private var homeList = ArrayList<String>()


    fun homeList(homArray: Array<String>): ArrayList<String> {
        for (hom in homArray) {
            homeList.add(hom)
        }
        return homeList
    }

}
