package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.NewsResult
import com.example.htkotlinmvvmdemo1.databinding.DialogLanguageItemBinding
import com.example.htkotlinmvvmdemo1.databinding.NewsItemBinding


class LanguageAdapter : BaseQuickAdapter<String, BaseViewBindingHolder<DialogLanguageItemBinding>>(R.layout.dialog_language_item) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<DialogLanguageItemBinding>, item: String) {
        holder.viewBinding.tvLanguage.text = item.substring(2,item.length)

    }


    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<DialogLanguageItemBinding> {
        val binding =
            DialogLanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }


}