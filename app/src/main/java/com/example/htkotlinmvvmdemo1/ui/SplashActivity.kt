package com.example.htkotlinmvvmdemo1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.acStart
import com.example.htkotlinmvvmdemo1.config.Constant.LOGINPASSWORD
import com.example.htkotlinmvvmdemo1.databinding.ActivitySplashBinding
import com.caspar.base.utils.MMKVUtil
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initIntent() {}
    override fun initView(savedInstanceState: Bundle?) {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        lifecycleScope.launchWhenCreated {
            delay(2000) //延时两秒跳转界面
            if (MMKVUtil.decodeString(LOGINPASSWORD).isEmpty()) {
                acStart<LoginActivity>()
            } else {
                acStart<MainActivity>()
            }
            finish()

        }
    }


}