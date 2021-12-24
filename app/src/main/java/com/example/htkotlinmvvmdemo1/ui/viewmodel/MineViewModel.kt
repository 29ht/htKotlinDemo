package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.AndroidViewModel
import com.caspar.base.helper.ActivityStackManager.application
import com.example.htkotlinmvvmdemo1.R

/**
 *  "ht" 创建 2021/12/22
 *   界面名称以及功能: 我的界面
 */
class MineViewModel(application: Application) : AndroidViewModel(application) {
    //我的列表

    val mineData = ArrayList<String>()

    //第三方列表
    val thirdPartyData = ArrayList<String>()

    var headPortrait = "https://api.xiaobaibk.com/api/qq.php?qq="

    fun mineDataList(stringArray: Array<String>): ArrayList<String> {
        for (cc in stringArray) {
            mineData.add(cc)
        }
        return mineData
    }
    fun thirdPartyDataList(stringArray: Array<String>): ArrayList<String> {
        for (cc in stringArray) {
            thirdPartyData.add(cc)
        }
        return thirdPartyData
    }
    //横向（transAnimation）and 纵向（scaleAnimation）出现动画
    fun imageAnimations(imageView: AppCompatImageView, duration: Long, isScale: Boolean) {
        if (isScale) {
            val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f)
            scaleAnimation.duration = duration   //显示时长
            scaleAnimation.interpolator = AccelerateDecelerateInterpolator()//动画显示规律
            imageView.startAnimation(scaleAnimation)
        } else {
            val transAnimation = TranslateAnimation(-800f, 0f, 0f, 0f)
            transAnimation.duration = duration
            transAnimation.interpolator = AccelerateDecelerateInterpolator()
            imageView.startAnimation(transAnimation)
        }

    }

    //横向出现动画
    fun textAnimations(textView: TextView, duration: Long) {
        val transAnimations = TranslateAnimation(0f, 0f, -800f, 0f)
        transAnimations.duration = duration
        transAnimations.interpolator = AccelerateDecelerateInterpolator()
        textView.startAnimation(transAnimations)
    }
}
