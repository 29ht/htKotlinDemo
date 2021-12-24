package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import com.caspar.base.base.BaseViewModel
import com.example.htkotlinmvvmdemo1.config.Constant
import com.caspar.base.utils.MMKVUtil

/**
 *  "ht" 创建 2021/12/23
 *   界面名称以及功能: 登录界面
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    fun userEmpty(userName: String, userPassword: String): Boolean {
        if (userName.isEmpty() || userName.isEmpty()) {
            toast("账号密码不能为空")
            return false
        } else {
            MMKVUtil.encode(Constant.LOGINUSERNAME, userName)
            MMKVUtil.encode(Constant.LOGINPASSWORD, userPassword)
            return true
        }
    }


}
