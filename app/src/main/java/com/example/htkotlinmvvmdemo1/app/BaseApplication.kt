package com.example.htkotlinmvvmdemo1.app

import android.app.Application
import android.content.res.Configuration
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.multidex.MultiDexApplication
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.caspar.base.helper.ActivityStackManager
import com.caspar.base.helper.LogUtil
import com.example.htkotlinmvvmdemo1.BuildConfig
import com.caspar.base.utils.language.SwitchLanguageUtils
import com.example.htkotlinmvvmdemo1.ui.CrashActivity
import com.example.htkotlinmvvmdemo1.ui.MainActivity
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.BlackToastStyle
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


/**
 * ht
 * 初始化Application
 */
class BaseApplication : MultiDexApplication(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        context = this
        init()
    }

    //第三方框架或本地工具类初始化
    private fun init() {
        //打印日志初始化,打正式包将不再打印日志
        LogUtil.init(BuildConfig.LOG_ENABLE, "ht")
        MMKV.initialize(this)//本地储存初始化

        //Toast弹框初始化
        ToastUtils.init(this)
        ToastUtils.setStyle(BlackToastStyle())
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 100)
        //全局堆栈管理初始化
        ActivityStackManager.init(context)
        // Crash 捕捉界面
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)
            .trackActivities(true)
            .minTimeBetweenCrashesMs(2000) // 重启的 Activity
            .restartActivity(MainActivity::class.java) // 错误的 Activity
            .errorActivity(CrashActivity::class.java) // 设置监听器
            //.eventListener(new YourCustomEventListener())
            .apply()

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        SwitchLanguageUtils().setAppLanguage(context)


    }

    companion object {
        //Application上下文
        lateinit var context: Application

        //全局使用的协程，因为官方不推荐使用GlobalScope，因此在Application中创建一个全局的协程以便于非Activity，ViewModel的类使用协程
        var job = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        init {
            //启用矢量图兼容
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
            //设置全局默认配置（优先级最低，会被其他设置覆盖）
        }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}
