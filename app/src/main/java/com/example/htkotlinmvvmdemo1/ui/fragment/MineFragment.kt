package com.example.htkotlinmvvmdemo1.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.caspar.base.base.BaseFragment
import com.caspar.base.ext.acStart
import com.caspar.base.ext.dp
import com.caspar.base.utils.MMKVUtil
import com.caspar.base.utils.language.SwitchLanguageUtils
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.config.Constant
import com.example.htkotlinmvvmdemo1.databinding.FragmentMineBinding
import com.example.htkotlinmvvmdemo1.dialog.LanguageDialog
import com.example.htkotlinmvvmdemo1.network.util.AppBarStateChangeListener
import com.example.htkotlinmvvmdemo1.ui.LoginActivity
import com.example.htkotlinmvvmdemo1.ui.SplashActivity
import com.example.htkotlinmvvmdemo1.ui.activity.GaodeMapActivity
import com.example.htkotlinmvvmdemo1.ui.adapter.MineAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.MineViewModel
import com.google.android.material.appbar.AppBarLayout


/**
 *  "ht" 创建 2021/12/22
 *   界面名称以及功能: 我的界面
 */
class MineFragment : BaseFragment<FragmentMineBinding>() {
    private val viewModel: MineViewModel by viewModels()
    private val mineAdapter: MineAdapter by lazy { MineAdapter() }
    private val mineHeaderAdapter: MineAdapter by lazy { MineAdapter() }

    var one = false

    @SuppressLint("InflateParams")
    override fun initView(savedInstanceState: Bundle?) {
        viewModel.imageAnimations(mBindingView.ivHeadPortrait, 2000, false)
        viewModel.textAnimations(mBindingView.tvGrade, 1000)
        viewModel.textAnimations(mBindingView.tvName, 1000)
        mBindingView.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            @SuppressLint("ResourceAsColor")
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when {
                    state === State.EXPANDED -> {
                        //展开状态
                        if (one) {
                            mBindingView.ivHeadPortrait.isInvisible = true
                            mBindingView.tvGrade.isGone = false
                            mBindingView.tvName.isGone = false
                            mBindingView.ivHeadPortrait.isInvisible = false
                            viewModel.imageAnimations(mBindingView.ivHeadPortrait, 1000, false)
                            viewModel.textAnimations(mBindingView.tvGrade, 1000)
                            viewModel.textAnimations(mBindingView.tvName, 1000)
                        } else {
                            mBindingView.tvGrade.isGone = false
                            mBindingView.tvName.isGone = false
                        }
                        one = true
                    }
                    state === State.COLLAPSED -> {
                        //折叠状态
                        if (one) {
                            mBindingView.ivHeadPortrait.isInvisible = true
                            mBindingView.tvGrade.isGone = true
                            mBindingView.tvName.isGone = true
                            mBindingView.ivHeadPortrait.isInvisible = false
                            viewModel.imageAnimations(mBindingView.ivHeadPortrait, 1000, true)

                        } else {
                            mBindingView.tvGrade.isInvisible = true
                            mBindingView.tvName.isInvisible = true
                        }
                    }
                    else -> {
                        //中间状态
                    }
                }
            }
        })


        mBindingView.recyMine.layoutManager = LinearLayoutManager(context)
        mBindingView.recyMine.addItemDecoration(Decoration.decoration2(2, 0, 0, 0))
        mBindingView.recyMine.adapter = mineAdapter
        mineAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                when (adapter.data[position].toString().substring(0, 1)) {
                    "3" -> {
                        //弹窗提示
                        val drawDialog = context?.let { LanguageDialog(it, R.style.BottomDialog) }
                        drawDialog?.show()
                        drawDialog?.languageAdapter?.setOnItemClickListener { adapter, _, position ->
                            when (adapter.data[position].toString().substring(0, 1)) {
                                "0" -> {
                                    MMKVUtil.encode(Constant.LANGUAGE, 0)
                                }
                                "1" -> {
                                    MMKVUtil.encode(Constant.LANGUAGE, 1)
                                }
                                "2" -> {
                                    MMKVUtil.encode(Constant.LANGUAGE, 2)
                                }
                            }
                            context?.let { SwitchLanguageUtils().setAppLanguage(it) }
                            acStart<SplashActivity>() {
                                addFlags(FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                            activity?.overridePendingTransition(
                                R.anim.activity_alpha_in,
                                R.anim.activity_alpha_out
                            )
                            activity?.finish()
                            drawDialog.dismiss()
                        }
                    }
                    "4" -> {
                        acStart<LoginActivity>()
                        activity?.finish()
                    }
                }
            }

        }
        val headerView: View = layoutInflater.inflate(R.layout.recycview, null)
        headerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val rHeaderView1 = headerView.findViewById<RecyclerView>(R.id.rv_list)
        rHeaderView1.layoutManager = LinearLayoutManager(context)
        rHeaderView1.addItemDecoration(Decoration.decoration2(2, 0, 0, 0))
        rHeaderView1.adapter = mineHeaderAdapter
        mineHeaderAdapter.setList(viewModel.thirdPartyDataList(resources.getStringArray(R.array.mine_list_head)))
        mineHeaderAdapter.draggableModule.isDragEnabled = true //依赖库自带拖拽功能
        mineHeaderAdapter.draggableModule.attachToRecyclerView(rHeaderView1) //绑定适配器才能拖拽
        mineHeaderAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                when (adapter.data[position].toString().substring(0, 1)) {
                    "7" -> {
                        acStart<GaodeMapActivity>()
                    }
                }
            }

        }
        val footerView: View = layoutInflater.inflate(R.layout.mine_footer, null)
        footerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT

        )
        footerView.findViewById<EditText>(R.id.etmine).isGone = true
        val headerView2: View = layoutInflater.inflate(R.layout.mine_footer, null)
        headerView2.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        headerView2.findViewById<TextView>(R.id.tv_footer).isGone = true
        headerView2.findViewById<EditText>(R.id.etmine).addTextChangedListener { text ->
            run {
                if (!text.isNullOrEmpty()) {
                    mBindingView.ivHeadPortrait.load(viewModel.headPortrait + text.toString()) {
                        transformations(
                            RoundedCornersTransformation(
                                topLeft = 60f.dp,
                                topRight = 60f.dp,
                                bottomLeft = 60f.dp,
                                bottomRight = 60f.dp
                            )
                        )
                    }
                    viewModel.imageAnimations(mBindingView.ivHeadPortrait, 1000, false)
                }
            }
        }
        mineAdapter.addHeaderView(headerView2)
        mineAdapter.addHeaderView(headerView)
        mineAdapter.addFooterView(footerView)
        mineAdapter.setList(viewModel.mineDataList(resources.getStringArray(R.array.mine_list)))
        mBindingView.ivHeadPortrait.load(viewModel.headPortrait + "2473575445") {
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