package com.example.htkotlinmvvmdemo1.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.dp
import com.caspar.base.helper.LogUtil
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.HotelCity
import com.example.htkotlinmvvmdemo1.databinding.ActivityRecyclerViewBinding
import com.example.htkotlinmvvmdemo1.ui.adapter.CityListAdapter
import com.example.htkotlinmvvmdemo1.ui.adapter.HotelCityListAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.RecyclerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecyclerViewActivity : BaseActivity<ActivityRecyclerViewBinding>() {
    private val recyclerViewModel: RecyclerViewModel by viewModels()
    private val cityListAdapter: CityListAdapter by lazy { CityListAdapter() }
    private val hotelCityListAdapter: HotelCityListAdapter by lazy { HotelCityListAdapter() }
    private var city: HotelCity = HotelCity()
    override fun initIntent() {
    }

    private fun trainInit() {
        mBindingView.title.tvCenter.text = R.string.city_serch.toString()
        mBindingView.title.tvLeft.setOnClickListener { finish() }
        mBindingView.trainRecy.adapter = cityListAdapter
        lifecycleScope.launch(Dispatchers.Main) {
            showDialog()
            val httpBean = withContext(Dispatchers.IO) {
                recyclerViewModel.httpGet()
            }
            stopDialog()
            if (httpBean == null) {
                toast("网络请求失败，错误详情请查看Studio的Logcat")
            } else {
                cityListAdapter.setList(httpBean.rows)
            }
        }


        mBindingView.etEnter.addTextChangedListener { text ->
            run {
                if (!text.isNullOrEmpty()) {
                    showDialog()
                    city.content = text.toString()
                    city.id = "1fe6e2da-e3d2-475f-826b-a49a3a614a3b"
                    city.language = "CN"
                    recyclerViewModel.HotleCity(city)
                }
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        trainInit()
        mBindingView.trainRecy.layoutManager = LinearLayoutManager(this)
        mBindingView.trainRecy.addItemDecoration(Decoration.decoration2(10.dp, 0, 0, 0))
        lifecycleScope.launch {
            recyclerViewModel.hotelCityslateResult.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        mBindingView.trainRecy.adapter = hotelCityListAdapter
                        hotelCityListAdapter.setList(it.data?.ElongLocationList)
                        stopDialog()
                    }
                    is NetworkResult.Error -> {
                        toast("Code:" + it.code + "--" + "Message:" + it.message)
                    }
                    else -> {
                        LogUtil.e("预留加载状态在此处可以弹一个加载框")
                    }

                }
            }
        }
    }


    override fun hideSoftByEditViewIds(): IntArray {
        return arrayListOf(R.id.et_enter).toIntArray()
    }
}






