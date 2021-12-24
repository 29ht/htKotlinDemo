package com.caspar.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.caspar.base.R

/*
* 全局调用加载dialog
*/
object ShowDialogUtil {
    private var pd: Dialog? = null
    fun showSearchDialog(context: Context?) {
        if (pd != null && pd!!.isShowing) {
            return
        }
        pd = Dialog(context!!, R.style.DialogTheme)
        pd!!.setContentView(R.layout.dialog_loading)
        pd!!.setCancelable(false)
        val window = pd!!.window!!
        val lp = window.attributes
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.MATCH_PARENT //宽高可设置具体大小
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        pd!!.window!!.attributes = lp
        pd!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        pd!!.show()
    }


    /*
     * 关闭数据加载对话框
     */
    fun stopDialog() {
        try {
            if (pd != null && pd!!.isShowing) {
                pd!!.dismiss()
            }
        } catch (ignored: Exception) {
        } finally {
            pd = null
        }
    }
}