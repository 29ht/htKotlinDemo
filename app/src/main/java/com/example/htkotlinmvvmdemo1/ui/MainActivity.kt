package com.example.htkotlinmvvmdemo1.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.caspar.base.adapter.FragmentPagerAdapter
import com.caspar.base.adapter.MainBodyPageChangeListener
import com.caspar.base.base.BaseActivity
import com.caspar.base.dialogchain.DialogChain
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.databinding.ActivityMainBinding
import com.example.htkotlinmvvmdemo1.dialog.ADialog
import com.example.htkotlinmvvmdemo1.dialog.BDialog
import com.example.htkotlinmvvmdemo1.dialog.CDialog
import com.example.htkotlinmvvmdemo1.ui.fragment.HomFragment
import com.example.htkotlinmvvmdemo1.ui.fragment.MineFragment
import com.example.htkotlinmvvmdemo1.ui.fragment.MusicFragment
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var dialogChain: DialogChain
    private val bDialog by lazy { BDialog(this) }
    private var itemImage = intArrayOf(R.mipmap.hom, R.drawable.musics, R.mipmap.pre)
    private var itemCheckedImage = intArrayOf(R.mipmap.hom1, R.drawable.music, R.mipmap.pre1)


    /*
     * 内容区域的适配器.
     */
    private var mFragmentPagerAdapter: FragmentPagerAdapter? = null

    /*
     * 内容的View.
     */
    private var pagerItemList: ArrayList<Fragment>? = null
    override fun initView(savedInstanceState: Bundle?) {


        mBindingView.title.tvLeft.isInvisible = true

    }

    override fun initIntent() {
//连续弹框
//        lifecycleScope.launch {
//            DialogChain.openLog(true)
//            createDialogChain()
//            delay(3000)
//            bDialog.onDataCallback("132132132132131")
//        }
        for (i in itemImage.indices) {
            mBindingView.crgp.addItem(itemImage[i], itemCheckedImage[i],  resources.getStringArray(R.array.main_list)[i])
        }


        val page1 = HomFragment()
        val page2 = MusicFragment()
        val page3 = MineFragment()
        val mFragments: MutableList<Fragment> = ArrayList()
        mFragments.add(page1)
        mFragments.add(page2)
        mFragments.add(page3)
        pagerItemList = ArrayList()
        pagerItemList!!.addAll(mFragments)
        mFragmentPagerAdapter = FragmentPagerAdapter(this, pagerItemList!!)
        mBindingView.mainViewpager.adapter = mFragmentPagerAdapter

        // 滑动
        val bodyChangeListener =
            MainBodyPageChangeListener(
                mBindingView.crgp,
                mBindingView.title.tvCenter,this
            )
        mBindingView.mainViewpager.registerOnPageChangeCallback(bodyChangeListener)
        // 取消预加载，默认只加载1个Fragment
        mBindingView.mainViewpager.offscreenPageLimit = 1
        mBindingView.crgp.checkedIndex = mBindingView.mainViewpager.currentItem
        mBindingView.crgp.setItemNewsCount(1, 100)
        mBindingView.crgp.setOnItemChangedListener {
            mBindingView.mainViewpager.setCurrentItem(
                mBindingView.crgp.checkedIndex, false
            )
        }
        mBindingView.mainViewpager.adapter?.notifyDataSetChanged()
    }

    //退出登录
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this)
                .setMessage("确定要退出吗？")
                .setTitle("提示")
                .setPositiveButton(R.string.dialog_submit) { _, _ ->
                    finish()
                }
                .setNeutralButton(R.string.dialog_cancel, null)
                .create()
                .show()

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /*
     *多dialog弹框
     */
    private fun createDialogChain() {
        dialogChain = DialogChain.create(3)
            .attach(this)
            .addInterceptor(ADialog(this))
            .addInterceptor(bDialog)
            .addInterceptor(CDialog(this))
            .build()

    }

    override fun onStart() {
        super.onStart()
        //   dialogChain.process()
    }


}


