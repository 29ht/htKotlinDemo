package com.example.htkotlinmvvmdemo1.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.caspar.base.helper.TimesUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.InfoList
import com.example.htkotlinmvvmdemo1.bean.response.Songlist
import com.example.htkotlinmvvmdemo1.databinding.MisicItemBinding

class SearchMusicListAdapter :
    BaseQuickAdapter<InfoList, BaseViewBindingHolder<MisicItemBinding>>(R.layout.misic_item) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<MisicItemBinding>, item: InfoList) {
        holder.viewBinding.tvMusicNo.text = (holder.layoutPosition + 1).toString()
        holder.viewBinding.tvMusicName.text = item.songname
        holder.viewBinding.tvMusicType.text = item.singername

        //        如果下标和传回来的下标相等 那么确定是点击的条目 把背景设置一下颜色
        if (holder.layoutPosition == getmPosition()) {
            holder.viewBinding.cardView.setCardBackgroundColor(context.resources.getColor(R.color.blue_2))
        } else {
//            否则的话就全白色初始化背景
            holder.viewBinding.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
        }

    }

    var mPosition: Int = 0
    fun getmPosition(): Int {
        return mPosition
    }

    fun setmPosition(mPosition: Int) {
        this@SearchMusicListAdapter.mPosition = mPosition
    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<MisicItemBinding> {
        val binding = MisicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }
}