package com.example.htkotlinmvvmdemo1.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.htkotlinmvvmdemo1.bean.response.sliderList
import com.youth.banner.adapter.BannerAdapter


/*
 * 自定义布局，下面是常见的图片样式
 */
class ImageAdapter(mDatas: ArrayList<sliderList>?) : BannerAdapter<sliderList, ImageAdapter.BannerViewHolder?>(mDatas) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = AppCompatImageView(parent.context)
//        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }


    class BannerViewHolder(@NonNull view: AppCompatImageView) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }

    override fun onBindView(holder: BannerViewHolder?, data: sliderList?, position: Int, size: Int) {
        holder!!.imageView.load(data?.picUrl)
    }
}
