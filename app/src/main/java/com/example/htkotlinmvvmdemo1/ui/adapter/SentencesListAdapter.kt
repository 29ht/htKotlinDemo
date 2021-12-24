package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.SentencesData
import com.example.htkotlinmvvmdemo1.databinding.MisicItemBinding

class SentencesListAdapter :
    BaseQuickAdapter<SentencesData, BaseViewBindingHolder<MisicItemBinding>>(
        R.layout.misic_item
    ) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<MisicItemBinding>, item: SentencesData) {
        holder.viewBinding.tvMusicNo.text = (holder.layoutPosition + 1).toString()
        holder.viewBinding.tvMusicName.text = item.name
        holder.viewBinding.tvMusicType.text = item.from

    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<MisicItemBinding> {
        val binding =
            MisicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }
}