package com.example.htkotlinmvvmdemo1.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import com.caspar.base.base.BaseActivity
import com.example.htkotlinmvvmdemo1.config.Constant.NWWSWEBVIEWURL
import com.example.htkotlinmvvmdemo1.databinding.ActivityWebViewBinding
import com.just.agentweb.AgentWeb


class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    private var agentWeb: AgentWeb? = null
    private var url: String? = null
    override fun initIntent() {
        url = intent.getStringExtra(NWWSWEBVIEWURL)
    }

    override fun initView(savedInstanceState: Bundle?) {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                mBindingView.llWeb, LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }



}