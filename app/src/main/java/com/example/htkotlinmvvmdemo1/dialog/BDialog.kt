package com.example.htkotlinmvvmdemo1.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.caspar.base.dialogchain.BaseDialog
import com.caspar.base.dialogchain.DialogChain
import com.caspar.base.utils.DensityUtil
import com.example.htkotlinmvvmdemo1.R

/**

 * Author：ht on 2021/12/16 10:15

 * 说明：

 */
class BDialog(context: Context) : BaseDialog(context), View.OnClickListener {
    private var data: String? = null

    // 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    fun onDataCallback(data: String) {
        this.data = data
        tryToShow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.attributes?.width = DensityUtil.dp2px(context,250f)
        window?.attributes?.height =DensityUtil.dp2px(context,300f)
        setContentView(R.layout.dialog_b)
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
        // 弹窗消失时把请求移交给下一个拦截器。
        setOnDismissListener {
            chain()?.process()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
   // 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    override fun intercept(chain: DialogChain) {
        super.intercept(chain)
        tryToShow()
    }

    private fun tryToShow() {
        // 只有同时满足这俩条件才能弹出来。
        if (data != null && chain() != null) {
            this.show()
        }
    }
}