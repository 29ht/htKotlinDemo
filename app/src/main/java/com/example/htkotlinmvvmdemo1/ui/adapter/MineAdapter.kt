package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.module.UpFetchModule
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.databinding.MineItemBinding

class MineAdapter :
    BaseQuickAdapter<String, BaseViewBindingHolder<MineItemBinding>>(R.layout.mine_item),
    DraggableModule,
    UpFetchModule {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<MineItemBinding>, item: String) {
        holder.viewBinding.mineCenter.text = item.substring(2, item.length)
    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<MineItemBinding> {
        val binding =
            MineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }
}