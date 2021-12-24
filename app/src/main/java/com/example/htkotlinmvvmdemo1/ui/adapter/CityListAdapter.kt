package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.Row
import com.example.htkotlinmvvmdemo1.databinding.TrainItemBinding

class CityListAdapter : BaseQuickAdapter<Row, BaseViewBindingHolder<TrainItemBinding>>(R.layout.train_item) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<TrainItemBinding>, item: Row) {
        holder.viewBinding.tvCountry.text = item.parent + "(" + item.adcode + ")"
        holder.viewBinding.tvCity.text = item.name
        holder.viewBinding.tvLng.text = item.lng.toString()
        holder.viewBinding.tvLat.text = item.lat.toString()


    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<TrainItemBinding> {
        val binding = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }



}