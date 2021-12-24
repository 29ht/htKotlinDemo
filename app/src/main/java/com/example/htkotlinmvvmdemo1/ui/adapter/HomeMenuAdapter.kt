package com.example.htkotlinmvvmdemo1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.module.UpFetchModule
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.databinding.ItemMenuBinding


/**
 *  @Create 2020/6/13.
 *  @Use
 */
class HomeMenuAdapter :
    BaseQuickAdapter<String, BaseViewBindingHolder<ItemMenuBinding>>(R.layout.item_menu),
    DraggableModule,
    UpFetchModule {
    //如果非要使用ViewBinding，则应该重写onCreateDefViewHolder方法，否则将会导致类型无法强转的Crash
    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<ItemMenuBinding> {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }

    override fun convert(holder: BaseViewBindingHolder<ItemMenuBinding>, item: String) {
        holder.viewBinding.btnName.text = item.substring(2, item.length)
    }
}