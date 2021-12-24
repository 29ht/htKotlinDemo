package com.caspar.base.dialogchain

import android.util.Log

/**

 * Author：ht on 2021/12/16 10:17

 * 说明：

 */

internal var isOpenLog = false

internal fun String.logI(tagObj: Any) {
    if (isOpenLog) {
        Log.i(tagObj.javaClass.simpleName, this)
    }
}