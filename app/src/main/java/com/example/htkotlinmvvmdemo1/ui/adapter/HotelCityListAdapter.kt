package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.ElongLocationLists
import com.example.htkotlinmvvmdemo1.databinding.TrainItemBinding

class HotelCityListAdapter : BaseQuickAdapter<ElongLocationLists, BaseViewBindingHolder<TrainItemBinding>>(
    R.layout.train_item) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<TrainItemBinding>, item: ElongLocationLists) {
        holder.viewBinding.tvCountry.text = item.BasicCityNameCn + "(" + item.BasicCityCode + ")"
        holder.viewBinding.tvCity.text = item.HotelMallName
        holder.viewBinding.tvLng.text = item.HotelMallName
        holder.viewBinding.tvLat.text = item.ParentName

    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewBindingHolder<TrainItemBinding> {
        val binding = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }
}