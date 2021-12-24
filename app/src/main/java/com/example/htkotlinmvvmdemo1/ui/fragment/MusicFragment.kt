package com.example.htkotlinmvvmdemo1.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.caspar.base.base.BaseFragment
import com.caspar.base.ext.acStart
import com.caspar.base.ext.dp
import com.caspar.base.helper.LogUtil
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.InfoList
import com.example.htkotlinmvvmdemo1.bean.response.Songlist
import com.example.htkotlinmvvmdemo1.bean.response.sliderList
import com.example.htkotlinmvvmdemo1.config.ApiConfig.qqCover
import com.example.htkotlinmvvmdemo1.config.Constant
import com.example.htkotlinmvvmdemo1.databinding.FragmentMusicBinding
import com.example.htkotlinmvvmdemo1.network.util.AppBarStateChangeListener
import com.example.htkotlinmvvmdemo1.ui.activity.WebViewActivity
import com.example.htkotlinmvvmdemo1.ui.adapter.ImageAdapter
import com.example.htkotlinmvvmdemo1.ui.adapter.QQMusicListAdapter
import com.example.htkotlinmvvmdemo1.ui.adapter.SearchMusicListAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.MusicViewModel
import com.google.android.material.appbar.AppBarLayout
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 *  "ht" 创建 2021/12/1
 *   界面名称以及功能: 音乐界面
 */
class MusicFragment : BaseFragment<FragmentMusicBinding>() {
    private val viewModel: MusicViewModel by viewModels()
    private val musicAdapter: QQMusicListAdapter by lazy { QQMusicListAdapter() }
    private val searchMusicAdapter: SearchMusicListAdapter by lazy { SearchMusicListAdapter() }

    private var qqList: ArrayList<Songlist>? = null
    private var bannerDatas: ArrayList<sliderList>? = null
    private var baneradapter: ImageAdapter? = null
    private var info: ArrayList<InfoList>? = null

    @SuppressLint("InflateParams")
    override fun initView(savedInstanceState: Bundle?) {


        mBindingView.recyMusic.layoutManager = LinearLayoutManager(context)

        viewModel.getMusicItem()
        viewModel.getBannerUrl()
        mBindingView.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when {
                    state === State.EXPANDED -> {
                        //展开状态
                        mBindingView.cardView.isInvisible = false
                    }
                    state === State.COLLAPSED -> {
                        //折叠状态
                       mBindingView.cardView.isInvisible = true
                        mBindingView.etEnter.hint = "歌曲搜索"
                    }
                    else -> {
                        //中间状态
                        mBindingView.cardView.isInvisible = false
                    }
                }
            }
        })
        mBindingView.misIv.setOnClickListener {
            viewModel.startImg(mBindingView.misIv, true)
            viewModel.startMusic(null, true)
        }
        //搜索可以播放的音乐
        mBindingView.etEnter.addTextChangedListener { text ->
            run {
                if (!text.isNullOrEmpty()) {
                    viewModel.getMusicsSearchUrl(text.toString())
                } else {
                    mBindingView.recyMusic.adapter = musicAdapter
                    musicAdapter.setList(qqList)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.qqMusicsSearchLateResult.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        info = it.data?.data?.info
                        mBindingView.recyMusic.adapter = searchMusicAdapter
                        searchMusicAdapter.setList(info)

                        searchMusicAdapter.addChildClickViewIds(R.id.cc_music_bg)
                        searchMusicAdapter.setOnItemChildClickListener { _, view, position ->
                            if (view.id == R.id.cc_music_bg) {
                                viewModel.getMusicsSearchStartUrl(info!![position].hash!!)
                                searchMusicAdapter.setmPosition(position)
                                mBindingView.tvMusic.text = info!![position].songname
                                mBindingView.tvMusicName.text = info!![position].singername
                                mBindingView.misIv.load(viewModel.misUrl) {
                                    transformations(
                                        RoundedCornersTransformation(
                                            topLeft = 60f.dp,
                                            topRight = 60f.dp,
                                            bottomLeft = 60f.dp,
                                            bottomRight = 60f.dp
                                        )
                                    )
                                }
                                viewModel.startImg(mBindingView.misIv, false)
                                searchMusicAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        //获取播放链接并播放（需要付费）的话没有链接地址
        lifecycleScope.launch {
            viewModel.qqMusicsSearchStartResult.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        if (it.data?.error.equals("需要付费")) {
                            toast("需要付费")
                        } else {
                            viewModel.startMusic(it.data, false)
                        }


                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.qqMusicsTokenLateResult.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        bannerDatas = it.data?.data?.slider
                        useBanner()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.qqMusicsLateResult.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        mBindingView.recyMusic.adapter = musicAdapter
                        musicAdapter.addChildClickViewIds(R.id.cc_music_bg)
                        musicAdapter.setOnItemChildClickListener { _, view, position ->
                            if (view.id == R.id.cc_music_bg) {
                                musicAdapter.setmPosition(position)

                                //   viewModel.StartMusic()
                                mBindingView.tvMusic.text = qqList!![position].data!!.songname
                                mBindingView.tvMusicName.text =
                                    qqList!![position].data!!.singer!![0].name
                                mBindingView.misIv.load(qqCover + qqList!![position].data?.albumid + "_0.jpg") {
                                    transformations(
                                        RoundedCornersTransformation(
                                            topLeft = 60f.dp,
                                            topRight = 60f.dp,
                                            bottomLeft = 60f.dp,
                                            bottomRight = 60f.dp
                                        )
                                    )
                                }
                                viewModel.startImg(mBindingView.misIv, false)
                                musicAdapter.notifyDataSetChanged()
                            }
                        }
                        qqList = it.data?.songlist
                        musicAdapter.setList(qqList)
                        mBindingView.tvMusic.text = it.data?.songlist?.get(0)?.data!!.songname
                        mBindingView.tvMusicName.text =
                            it.data.songlist?.get(0)?.data!!.singer!![0].name
                        if (it.data.songlist!![0].data?.albumid!!.isEmpty()) {
                            mBindingView.misIv.load(viewModel.getIvData()) {
                                transformations(
                                    RoundedCornersTransformation(
                                        topLeft = 60f.dp,
                                        topRight = 60f.dp,
                                        bottomLeft = 60f.dp,
                                        bottomRight = 60f.dp
                                    )
                                )
                            }
                            viewModel.startImg(mBindingView.misIv, false)


                        } else {
                            mBindingView.misIv.load(qqCover + it.data.songlist!![0].data?.albumid + "_0.jpg") {
                                transformations(
                                    RoundedCornersTransformation(
                                        topLeft = 60f.dp,
                                        topRight = 60f.dp,
                                        bottomLeft = 60f.dp,
                                        bottomRight = 60f.dp
                                    )
                                )
                            }
                        }

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


    private fun useBanner() {
        //--------------------------简单使用-------------------------------
//        mBindingView.banner.addBannerLifecycleObserver(this) //添加生命周期观察者
//            .setAdapter(BannerExampleAdapter(DataBean.getTestData()))
//            .setIndicator(CircleIndicator(this))

        //—————————————————————————如果你想偷懒，而又只是图片轮播———————
        // —————————————————
        baneradapter = ImageAdapter(bannerDatas)
        mBindingView.banner.setAdapter(baneradapter)
            .addBannerLifecycleObserver(activity).indicator = CircleIndicator(activity)
        baneradapter!!.setOnBannerListener { data, _ ->
            acStart<WebViewActivity> {
                putExtra(Constant.NWWSWEBVIEWURL, data.linkUrl)
            }

        }
    }

}