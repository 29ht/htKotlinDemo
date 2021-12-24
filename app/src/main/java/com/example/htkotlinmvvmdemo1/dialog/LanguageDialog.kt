package com.example.htkotlinmvvmdemo1.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caspar.base.ext.acStart
import com.caspar.base.utils.DensityUtil
import com.caspar.base.utils.MMKVUtil
import com.caspar.base.utils.language.SwitchLanguageUtils
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.config.Constant
import com.example.htkotlinmvvmdemo1.ui.SplashActivity
import com.example.htkotlinmvvmdemo1.ui.adapter.LanguageAdapter

/**
 * Author：ht on 2021/12/16 10:15
 * 说明：
 */
class LanguageDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var recyclerView: RecyclerView? = null
    val languageAdapter: LanguageAdapter by lazy { LanguageAdapter() }
    private var languageList = ArrayList<String>()
    var mContext: Context = context

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.CENTER)
        window?.setWindowAnimations(R.style.Animation_Design_BottomSheetDialog)
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.recycview, null)
        initView(view)
        setContentView(view)

    }

    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.rv_list)
        recyclerView?.layoutManager = LinearLayoutManager(mContext)
        recyclerView?.addItemDecoration(Decoration.decoration2(2, 0, 0, 0))
        recyclerView?.adapter = languageAdapter
        //数据的话可以在外部实现 看情况写
        languageAdapter.setList(languageList())

    }


    override fun show() {
        super.show()
        val layoutParams = window?.attributes
        layoutParams?.gravity = Gravity.BOTTOM
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.decorView?.setPadding(
            DensityUtil.dp2px(mContext, 8f),
            0,
            DensityUtil.dp2px(mContext, 8f),
            DensityUtil.dp2px(mContext, 8f)
        )
        window?.attributes = layoutParams
    }

    private fun languageList(): ArrayList<String> {
        for (hom in context.resources.getStringArray(R.array.mine_language)) {
            languageList.add(hom)
        }
        return languageList
    }
}