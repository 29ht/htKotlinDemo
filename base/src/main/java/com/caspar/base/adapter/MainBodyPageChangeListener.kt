package com.caspar.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.caspar.base.R
import com.caspar.base.widget.CustomRadioGroup

class MainBodyPageChangeListener(
    private val customRadioGroup: CustomRadioGroup,
    private val textView: TextView,
    private val context: Context
) : OnPageChangeCallback() {
    override fun onPageScrollStateChanged(arg0: Int) {}
    override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
        if (arg1 != 0.0f) {
            val right: Int
            val left: Int
            if (arg0 == customRadioGroup.checkedIndex) {
                left = customRadioGroup.checkedIndex
                right = customRadioGroup.checkedIndex + 1
            } else {
                left = customRadioGroup.checkedIndex - 1
                right = customRadioGroup.checkedIndex
            }
            customRadioGroup.itemChangeChecked(left, right, arg1)
        } else {
            customRadioGroup.checkedIndex = arg0
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onPageSelected(arg0: Int) {
        if (arg0 == 0) {
            //    AnimationUtils.showAndHiddenAnimation(customRadioGroup, AnimationUtils.AnimationState.STATE_SHOW_UP, 300);
            //    AnimationUtils.showAndHiddenAnimation(view_crgp, AnimationUtils.AnimationState.STATE_HIDDEN_UP, 300);
            textView.text = context.resources.getStringArray(R.array.main_list)[0]
        } else if (arg0 == 1) {

            //     AnimationUtils.showAndHiddenAnimation(customRadioGroup, AnimationUtils.AnimationState.STATE_HIDDEN_UP, 0);
            //    AnimationUtils.showAndHiddenAnimation(view_crgp, AnimationUtils.AnimationState.STATE_SHOW_UP, 0);
            //  AnimationUtils.showAndHiddenAnimation(view_crgp, AnimationUtils.AnimationState.STATE_HIDDEN_UP, 0);
            textView.text = context.resources.getStringArray(R.array.main_list)[1]
        } else if (arg0 == 2) {
            //   AnimationUtils.showAndHiddenAnimation(customRadioGroup, AnimationUtils.AnimationState.STATE_SHOW_UP, 300);
            //  AnimationUtils.showAndHiddenAnimation(view_crgp, AnimationUtils.AnimationState.STATE_HIDDEN_UP, 300);
            textView.text = context.resources.getStringArray(R.array.main_list)[2]
        }
    }
}