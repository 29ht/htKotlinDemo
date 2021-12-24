package com.example.htkotlinmvvmdemo1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.TestData
import com.example.htkotlinmvvmdemo1.databinding.TrainItemBinding

class GroupAdapter : BaseQuickAdapter<TestData, BaseViewBindingHolder<TrainItemBinding>>(R.layout.train_item) {
    var testDataList: MutableList<TestData>? = null
    fun dataAdapter(testDataList: MutableList<TestData>?) {
        this.testDataList = testDataList
    }

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewBindingHolder<TrainItemBinding>, item: TestData) {
        holder.viewBinding.tvCountry.text = item.text
    }

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewBindingHolder<TrainItemBinding> {
        val binding = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewBindingHolder(binding)
    }

    /*
     * 判断position对应的Item是否是组的第一项
     *
     * @param position
     * @return
     */
    fun isItemHeader(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val lastGroupName: String = testDataList!!.get(position - 1).getGroupName()
            val currentGroupName: String = testDataList!!.get(position).getGroupName()
            //判断上一个数据的组别和下一个数据的组别是否一致，如果不一致则是不同组，也就是为第一项（头部）
            lastGroupName != currentGroupName
        }
    }

    /*
     * 获取position对应的Item组名
     *
     * @param position
     * @return
     */
    fun getGroupName(position: Int): String {
        return testDataList!!.get(position).getGroupName()
    }
}