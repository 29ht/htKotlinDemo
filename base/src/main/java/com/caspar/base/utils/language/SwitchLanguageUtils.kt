package com.caspar.base.utils.language

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.caspar.base.utils.MMKVUtil

import java.util.*


class SwitchLanguageUtils {
    /**
     * @author ht
     * @date 2021/12/23
     * @desc
     */

    /**
     * Activity 更新语言资源
     */
    fun getAttachBaseContext(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return setAppLanguageApi24(context)
        } else {
            setAppLanguage(context)
        }
        return context
    }

    /**
     * 设置应用语言
     */
    @Suppress("DEPRECATION")
    fun setAppLanguage(context: Context) {
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        // 获取当前系统语言，默认设置跟随系统
        val locale = getAppLocale()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, displayMetrics)
    }

    /**
     * 兼容 7.0 及以上
     */
    @TargetApi(Build.VERSION_CODES.N)
    private fun setAppLanguageApi24(context: Context): Context {
        val locale = getAppLocale()
        val resource = context.resources
        val configuration = resource.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }

    /**
     * 获取 App 当前语言
     */
    private fun getAppLocale() = when (MMKVUtil.decodeInt("LANGUAGE")) {
        0 -> { // 跟随系统
            getSystemLocale()
        }
        1 -> { // 中文
            Locale.CHINA
        }
        2 -> { // 英文
            Locale.ENGLISH
        }
        else -> Locale.ENGLISH
    }

    /**
     * 获取当前系统语言，如未包含则默认英文
     */
    private fun getSystemLocale(): Locale {
        val systemLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0]
        } else {
            Locale.getDefault()
        }
        return when (systemLocale.language) {
            Locale.CHINA.language -> {
                Locale.CHINA
            }
            Locale.ENGLISH.language -> {
                Locale.ENGLISH
            }
            else -> {
                Locale.ENGLISH
            }
        }

    }
}