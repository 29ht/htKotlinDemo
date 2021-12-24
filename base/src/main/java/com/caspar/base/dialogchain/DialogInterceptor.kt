package com.caspar.base.dialogchain

/*

 * Author：ht on 2021/12/16 10:17

 * 说明：

 */
interface DialogInterceptor {
    fun intercept(chain: DialogChain)
}