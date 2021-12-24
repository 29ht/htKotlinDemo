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
import com.example.htkotlinmvvmdemo1.databinding.NewsItemBinding


class NewsListAdapter : BaseQuickAdapter<NewsResult, BaseViewBindingHolder<NewsItemBinding>>(R.layout.news_item) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<NewsItemBinding>, item: NewsResult) {
        if (item.publish_time.isNullOrEmpty()){
            holder.viewBinding.tvTxandwy.text="网易新闻"
            holder.viewBinding.tvNewsTitle.text = item.title
            holder.viewBinding.tvNewsImage.load(item.image)
            holder.viewBinding.tvPasstime.text = item.passtime
        }else{
            holder.viewBinding.tvTxandwy.text="腾讯新闻"
            holder.viewBinding.tvNewsTitle.text = item.title
            holder.viewBinding.tvNewsImage.load(item.img)
            holder.viewBinding.tvPasstime.text = item.publish_time
        }

    }


    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<NewsItemBinding> {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }


}