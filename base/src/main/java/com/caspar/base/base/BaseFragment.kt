package com.caspar.base.base

import android.app.Activity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.caspar.base.action.ToastAction
import com.caspar.base.helper.LogUtil
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/*
 * @author ht
 * @time 2021/12/10
 */
abstract class BaseFragment<SV : ViewBinding> : Fragment(), ToastAction {
    protected lateinit var mBindingView: SV


    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private var hasCreateView: Boolean = false

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private var isFragmentVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBindView(inflater, container)
    }


    //初始化ViewBinding
    private fun initBindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val superclass = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            mBindingView = method.invoke(null, inflater, container, false) as SV
        } catch (e: NoSuchMethodException) {
            LogUtil.e(e)
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            LogUtil.e(e)
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            LogUtil.e(e)
            e.printStackTrace()
        }
        return mBindingView.root
    }



    //获取父Activity
    fun getParentActivity(): Activity? {
        return activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)

    }

    abstract fun initView(savedInstanceState: Bundle?)

}