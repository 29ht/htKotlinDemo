package com.example.htkotlinmvvmdemo1.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.acStart
import com.example.htkotlinmvvmdemo1.databinding.ActivityLoginBinding
import com.example.htkotlinmvvmdemo1.ui.viewmodel.LoginViewModel


/**
 *  "ht" 创建 2021/12/23
 *   界面名称以及功能: 登录界面
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun initIntent() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mBindingView.login.setOnClickListener {
            if (loginViewModel.userEmpty(mBindingView.username.text.toString(), mBindingView.password.text.toString())){
                acStart<MainActivity>()
                finish()
            }
        }
    }
}