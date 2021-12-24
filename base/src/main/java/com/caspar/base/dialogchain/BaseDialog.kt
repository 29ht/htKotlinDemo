package com.caspar.base.dialogchain

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog

/**

 * Author：ht on 2021/12/16 10:15

 * 说明：

 */
abstract class BaseDialog(context: Context) : AlertDialog(context), DialogInterceptor {

    private var mChain: DialogChain? = null

    /*下一个拦截器*/
    fun chain(): DialogChain? = mChain

    @CallSuper
    override fun intercept(chain: DialogChain) {
        mChain = chain
    }





}