package com.example.htkotlinmvvmdemo1.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.caspar.base.base.BaseActivity
import com.example.htkotlinmvvmdemo1.bean.TestData
import com.example.htkotlinmvvmdemo1.databinding.ActivityNewsBinding
import com.example.htkotlinmvvmdemo1.network.util.decoration.GroupDecoration
import com.example.htkotlinmvvmdemo1.ui.adapter.GroupAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import java.util.*

class GroupDecorationActivity : BaseActivity<ActivityNewsBinding>() {
    private var testDataList: MutableList<TestData>? = null
    private val groupAdapter: GroupAdapter by lazy { GroupAdapter() }


    @SuppressLint("DefaultLocale")
    private fun initData() {
        testDataList = ArrayList<TestData>()
        for (i in 0..5) {
            testDataList!!.add(TestData(String.format("第一组%d号", i + 1), "第一组"))
        }
        for (i in 0..7) {
            testDataList!!.add(TestData(String.format("第二组%d号", i + 1), "第二组"))
        }
        for (i in 0..9) {
            testDataList!!.add(TestData(String.format("第三组%d号", i + 1), "第三组"))
        }
        for (i in 0..9) {
            testDataList!!.add(TestData(String.format("第四组%d号", i + 1), "第四组"))
        }
        for (i in 0..4) {
            testDataList!!.add(TestData(String.format("第五组%d号", i + 1), "第五组"))
        }
        for (i in 0..19) {
            testDataList!!.add(TestData(String.format("第六组%d号", i + 1), "第六组"))
        }
        for (i in 0..19) {
            testDataList!!.add(TestData(String.format("第七组%d号", i + 1), "第七组"))
        }
    }

    override fun initIntent() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        initData()
        mBindingView.tvSwitch.visibility = View.GONE
        mBindingView.refreshLayout.setRefreshHeader(ClassicsHeader(this))
        mBindingView.refreshLayout.setRefreshFooter(ClassicsFooter(this))
        mBindingView.refreshLayout.setOnRefreshListener {
            mBindingView.refreshLayout.finishRefresh(true) //传入false表示刷新失败
        }
        mBindingView.mRvCommonList.layoutManager = LinearLayoutManager(this)
        groupAdapter.dataAdapter(testDataList)
        mBindingView.mRvCommonList.addItemDecoration(GroupDecoration(this))
        mBindingView.mRvCommonList.adapter = groupAdapter
        groupAdapter.setList(testDataList)
    }

}