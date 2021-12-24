package com.example.htkotlinmvvmdemo1.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.acStart
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.config.Constant.NWWSWEBVIEWURL
import com.example.htkotlinmvvmdemo1.databinding.ActivityNewsBinding
import com.example.htkotlinmvvmdemo1.ui.adapter.NewsListAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.NewsViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
 *  "ht" 创建 2021/12/8.
 *   界面名称以及功能: 新闻列表,运用了智能刷新框架实现更多加载方法
 */
class NewsActivity : BaseActivity<ActivityNewsBinding>() {
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsListAdapter by lazy { NewsListAdapter() }
    private var isList = false


    /***初始化视图数据***/
    override fun initIntent() {
    }

    override fun initView(savedInstanceState: Bundle?) {

        initialization()
        netWorkRequest()
        clickEvent()
    }

    /*
     * 初始化数据
     */
    private fun initialization() {
        mBindingView.tvSwitch.text = getString(R.string.add_tx_news)

        mBindingView.refreshLayout.setRefreshHeader(ClassicsHeader(this))
        mBindingView.refreshLayout.setRefreshFooter(ClassicsFooter(this))
        mBindingView.refreshLayout.setOnRefreshListener {
            mBindingView.refreshLayout.finishRefresh(true) //传入false表示刷新失败
        }
        mBindingView.mRvCommonList.adapter = newsAdapter
        //设置布局管理器  layout可以设置方向
        mBindingView.mRvCommonList.layoutManager = LinearLayoutManager(this)
        mBindingView.mRvCommonList.addItemDecoration(Decoration.decoration2(0, 0, 0, 0))

        viewModel.wyiNewsModel()
    }

    /*
     * 网络请求数据
     */
    private fun netWorkRequest() {
        lifecycleScope.launch {
            viewModel.news.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        if (isList) {
                            newsAdapter.addData(it.data?.data!!)
                            viewModel.txAddAll(it.data.data)
                        } else {
                            newsAdapter.addData(it.data?.result!!)
                            viewModel.wyAddAll(it.data.result)
                        }
                    }
                    is NetworkResult.Error -> {
                        toast("Code:" + it.code + "--" + "Message:" + it.message)
                    }
                }
            }
        }
    }

    /*
     * 点击事件
     */
    private fun clickEvent() {
        //切换网易腾讯点击事件
        mBindingView.tvSwitch.setOnClickListener {
            if (isList) {
                mBindingView.tvSwitch.text = getString(R.string.switch_tx_news)
                newsAdapter.setList(viewModel.wyAll())
                isList = false
            } else {
                mBindingView.tvSwitch.text =  getString(R.string.switch_wy_news)
                if (viewModel.txAll().size > 0) {
                    newsAdapter.setList(viewModel.txAll())
                } else {
                    viewModel.txNewsModel()
                }

                isList = true
            }
        }

        //列表点击事件跳转WebViewActivity加载网页
        newsAdapter.setOnItemClickListener { _, _, position ->
            acStart<WebViewActivity> {
                if (isList) {
                    putExtra(NWWSWEBVIEWURL, viewModel.txAll()[position].url)
                } else {
                    putExtra(NWWSWEBVIEWURL, viewModel.wyAll()[position].path)
                }
            }
        }
        //加载更多数据
        mBindingView.refreshLayout.setOnLoadMoreListener {
            if (isList) {
                viewModel.txNewsModel()
            } else {
                viewModel.wyiNewsModel()
            }
            //这里关闭了下拉刷新 true为关闭
            mBindingView.refreshLayout.finishLoadMore(true)
        }

    }

}