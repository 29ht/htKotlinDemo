package com.example.htkotlinmvvmdemo1.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.acStart
import com.caspar.base.ext.requestMultiplePermissions
import com.caspar.xl.utils.decoration.Decoration
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.Hotels
import com.example.htkotlinmvvmdemo1.bean.response.requestHotel
import com.example.htkotlinmvvmdemo1.config.Constant
import com.example.htkotlinmvvmdemo1.databinding.ActivityHotelListBinding
import com.example.htkotlinmvvmdemo1.network.util.GsonUtils
import com.example.htkotlinmvvmdemo1.ui.adapter.HotelListAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.HotelViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HotelListActivity : BaseActivity<ActivityHotelListBinding>() {
    private val viewModel: HotelViewModel by viewModels()
    private val hotelAdapter: HotelListAdapter by lazy { HotelListAdapter() }
    private var skeletonScreen: RecyclerViewSkeletonScreen? = null
    private var hotels = ArrayList<Hotels>()
    override fun initIntent() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        initialization()
        netWorkRequest()
        clickEvent()
    }

    //测试骨架屏
    private fun skeletonScreen() {
        skeletonScreen = Skeleton.bind(mBindingView.hotelList)
            .shimmer(true)
            .count(3)
            .adapter(hotelAdapter)
            .load(R.layout.activity_hotel_list_screen)
            .duration(800)
            .color(R.color.white35)
            .show()

    }

    /*
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private fun initialization() {
        mBindingView.title.tvCenter.text = resources.getString(R.string.hotel_list_title)
        mBindingView.title.tvRight.text =resources.getString(R.string.map)
        //   showDialog()


        viewModel.hotelListModel(
            requestHotel().copy(
                Language = "CN",
                id = "212939f4-6748-42c9-9f8e-c55cdf822740",
                queryKey = "SHA,,,,2022-01-27,2022-01-28,0-1-2-3-4-5,0,0,1,1,1,2000,,"
            )
        )
        mBindingView.hotelList.adapter = hotelAdapter
        mBindingView.hotelList.layoutManager = LinearLayoutManager(this)
        mBindingView.hotelList.addItemDecoration(Decoration.decoration2(10, 0, 0, 0))
        skeletonScreen()
        hotelAdapter.setOnItemClickListener { _, view, ps ->
            when (ps) {
                0 -> {
                    //1. AlphaAnimation(透明)，实现
                    val alphaAnimation = AlphaAnimation(1.0f, 0.0f)//从全不透明变为全透明
                    // 3: 确定持续时间
                    alphaAnimation.duration = 2000
                    // 4: 确定Interpolator
                    alphaAnimation.interpolator = LinearInterpolator()
                    view.startAnimation(alphaAnimation)
                }
                1 -> {
                    //2.RotateAnimation(旋转)，实现
                    val rotateAnimation = RotateAnimation(0f, 360f)
                    rotateAnimation.duration = 2000
                    rotateAnimation.interpolator = AccelerateDecelerateInterpolator()
                    view.startAnimation(rotateAnimation)
                }
                2 -> {
                    //3.ScaleAnimation(放大缩小)动画实现
                    //放大五倍
                    val scaleAnimation = ScaleAnimation(0f, 5f, 0f, 5f)
                    scaleAnimation.duration = 2000   //显示时长
                    scaleAnimation.interpolator = AccelerateDecelerateInterpolator()//动画显示规律
                    view.startAnimation(scaleAnimation)

                }
                3 -> {
                    //4 .ScaleAnimation(位移)动画实现
                    val transAnimation = TranslateAnimation(-800f, 0f, 0f, 0f)
                    transAnimation.duration = 2000
                    transAnimation.interpolator = AccelerateDecelerateInterpolator()
                    view.startAnimation(transAnimation)

                }
                4 -> {
                    // 4.补间动画组合使用
                    val scaleAnimation = ScaleAnimation(1f, 0.5f, 1f, 0.5f)
                    val transAnimation = TranslateAnimation(
                        0f,
                        view.x - view.width / 2,
                        0f,
                        view.y - view.y + view.height
                    )
                    val alphaAnimation = AlphaAnimation(1.0f, 0.0f)//从全不透明变为全透明
                    view.startAnimation(alphaAnimation)
                    val aniSet = AnimationSet(false)
                    aniSet.duration = 2000
                    aniSet.interpolator = LinearInterpolator()
                    aniSet.addAnimation(scaleAnimation)
                    aniSet.addAnimation(transAnimation)
                    aniSet.addAnimation(alphaAnimation)
                    view.startAnimation(aniSet)

                }
            }


        }
    }

    /*
 * 点击事件
 */
    private fun clickEvent() {
        //带值跳转高德地图（这里要先申请定位权限）
        mBindingView.title.tvRight.setOnClickListener {
            permission.launch(com.caspar.base.helper.Permission.Group.LOCATION)

        }
    }

    //请求位置所需的权限
    private val permission = requestMultiplePermissions(allGranted = {
        acStart<GaodeMapActivity> {
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constant.NWWSWEBVIEWURL, hotels)
            putExtras(bundle)
        }
    }, denied = {
        toast("你拒绝了以下权限->${GsonUtils.toJson(it)}")
    }, explained = {
        toast("你拒绝了以下权限，并点击了不再询问->${GsonUtils.toJson(it)}")
    })

    /*
   * 网络请求数据
   */
    private fun netWorkRequest() {
        lifecycleScope.launch {
            viewModel.hotel.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        hotelAdapter.setList(it.data?.hotels)
                        hotels = it.data?.hotels!!
                        skeletonScreen?.hide()
                    }
                    is NetworkResult.Error -> {
                        stopDialog()
                        toast("Code:" + it.code + "message" + it.message)
                    }
                }

            }

        }
    }


}