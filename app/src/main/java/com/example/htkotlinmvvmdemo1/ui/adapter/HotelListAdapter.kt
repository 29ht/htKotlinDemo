package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.Hotels
import com.example.htkotlinmvvmdemo1.bean.response.sliderList
import com.example.htkotlinmvvmdemo1.databinding.HotelListItemBinding
import com.youth.banner.indicator.CircleIndicator

class HotelListAdapter :
    BaseQuickAdapter<Hotels, BaseViewBindingHolder<HotelListItemBinding>>(R.layout.activity_hotel_list) {
    private var imageAdapter: ImageAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<HotelListItemBinding>, item: Hotels) {

        //4 .ScaleAnimation(位移)动画实现
        val transAnimation = TranslateAnimation(-1000f, 0f, 0f, 0f)
        transAnimation.duration = 800
        transAnimation.interpolator = AccelerateDecelerateInterpolator()
        holder.viewBinding.cardView.startAnimation(transAnimation)


        val list = ArrayList<sliderList>()
        val slider1 = sliderList()
        val slider2 = sliderList()
        val slider3 = sliderList()
        val slider4 = sliderList()
        val slider5 = sliderList()
        slider1.picUrl = "https://online.bcdtravel.cn//appimage//X4ZW//hotel.png"
        slider2.picUrl = "https://online.bcdtravel.cn/appimage/X4ZW/airD.png"
        slider3.picUrl = "https://online.bcdtravel.cn/appimage/X4ZW/airI.png"
        slider4.picUrl = "https://online.bcdtravel.cn/appimage/X4ZW/train.png"
        slider5.picUrl = "https://online.bcdtravel.cn/appimage/X4ZW/car.png"
        list.add(slider1)
        list.add(slider2)
        list.add(slider3)
        list.add(slider4)
        list.add(slider5)
        imageAdapter = ImageAdapter(list)
        holder.viewBinding.banner.setAdapter(imageAdapter).indicator = CircleIndicator(context)
        holder.viewBinding.banner.isAutoLoop(true)//控制是否自动滑动
        holder.viewBinding.tvCityName.text = item.HotelName
        holder.viewBinding.tvCityDetailed.text = item.HotelAddress
        holder.viewBinding.tvPice.text = item.Price
        holder.viewBinding.tvPiceDay.text = item.Currency + "/Night"
        if (!item.HotelRating.equals("0")) {
            holder.viewBinding.tvHotelRating.text = item.HotelRating
        }
        if (!TextUtils.isEmpty(item.StarLevelDes)) {
            holder.viewBinding.tvHotelStarLevelDes.text = item.StarLevelDes
            if (!item.StarLevel.isNullOrEmpty()) {
                when (item.StarLevel) {
                    "5" -> holder.viewBinding.tvHotelStarLevelDes.setBackgroundResource(R.color.hotel_5)
                    "4" -> holder.viewBinding.tvHotelStarLevelDes.setBackgroundResource(R.color.hotel_4)
                    "3" -> holder.viewBinding.tvHotelStarLevelDes.setBackgroundResource(R.color.hotel_3)
                    "2" -> holder.viewBinding.tvHotelStarLevelDes.setBackgroundResource(R.color.hotel_2)
                    "1" -> holder.viewBinding.tvHotelStarLevelDes.setBackgroundResource(R.color.hotel_1)
                    "0" -> holder.viewBinding.tvHotelStarLevelDes.visibility = GONE
                }
            }
        }
    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<HotelListItemBinding> {
        val binding =
            HotelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }


}