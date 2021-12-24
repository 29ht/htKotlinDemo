package com.example.htkotlinmvvmdemo1.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.caspar.base.base.BaseFragment
import com.caspar.base.ext.acStart
import com.caspar.base.ext.dp
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.databinding.RecycviewBinding
import com.example.htkotlinmvvmdemo1.ui.activity.*
import com.example.htkotlinmvvmdemo1.ui.adapter.HomeMenuAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.HomeViewModel


/**
 *  @Create 2020/6/13.
 *  @Use
 */

class HomFragment : BaseFragment<RecycviewBinding>() {
    //首页列表适配器
    private val mAdapter: HomeMenuAdapter by lazy { HomeMenuAdapter() }

    //首页ViewModel
    private val mViewModel: HomeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {

        initAdapter()
    }

    @SuppressLint("ResourceType")
    private fun initAdapter() {
        mBindingView.rvList.layoutManager = GridLayoutManager(context, 2)
        mBindingView.rvList.addItemDecoration(Decoration.GridDecoration(2, 10.dp, true))
        mBindingView.rvList.adapter = mAdapter
        mAdapter.setList(mViewModel.homeList(resources.getStringArray(R.array.home_list)))
        mAdapter.setOnItemClickListener { _, _, position ->
            run {
                when (mAdapter.data[position].substring(0, 1)) {
                    "1" -> {
                        acStart<NewsActivity>()
                    }
                    "2" -> {
                        acStart<HotelListActivity>()
                    }
                    "3" -> {
                        acStart<GroupDecorationActivity>()
                    }
                    "4" -> {
                        acStart<SentencesActivity> {}
                    }
                    "5" -> {
                        acStart<RecyclerViewActivity> { }
                    }
                    "6" -> {
                        acStart<PaletteActivity>()
                    }
                    "7" -> {
                        //     startSelectFile2AllStorage()
                    }
                    "8" -> {
                        acStart<RoomActivity>()
                    }
                    "9" -> {

                    }

                }
            }
        }

        mAdapter.draggableModule.isDragEnabled = true //依赖库自带拖拽功能
        mAdapter.draggableModule.attachToRecyclerView(mBindingView.rvList) //绑定适配器才能拖拽
    }


}