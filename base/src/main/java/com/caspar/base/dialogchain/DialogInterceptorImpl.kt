package com.caspar.base.dialogchain

import androidx.annotation.CallSuper

/**

 * Author：ht on 2021/12/16 10:17

 * 说明：

 */
class DialogInterceptorImpl : DialogInterceptor {
    private var mChain: DialogChain? = null

    @CallSuper
    override fun intercept(chain: DialogChain) {
        mChain = chain
    }


    /*执行下一个拦截器*/
    fun chain(): DialogChain? = mChain

}