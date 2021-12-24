package com.example.htkotlinmvvmdemo1.ui.activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.caspar.base.base.BaseActivity
import com.caspar.base.ext.dp
import com.caspar.xl.utils.decoration.Decoration
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.SentencesData
import com.example.htkotlinmvvmdemo1.databinding.ActivityDailyverseBinding
import com.example.htkotlinmvvmdemo1.ui.adapter.SentencesListAdapter
import com.example.htkotlinmvvmdemo1.ui.viewmodel.SentencesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class SentencesActivity : BaseActivity<ActivityDailyverseBinding>() {
    private val viewmedel: SentencesViewModel by viewModels()
    private val sentencesListAdapter: SentencesListAdapter by lazy { SentencesListAdapter() }
    private var data = ArrayList<SentencesData>()
    override fun initIntent() {
    }
    override fun initView(savedInstanceState: Bundle?) {
        mBindingView.btnNext.setOnClickListener {
            showDialog()
            viewmedel.getBaiduImage()
        }
        mBindingView.trainRecy.layoutManager = LinearLayoutManager(this)
        mBindingView.trainRecy.addItemDecoration(Decoration.decoration2(10.dp, 0, 0, 0))
        lifecycleScope.launch {
            viewmedel.dailyVerse.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        mBindingView.trainRecy.adapter = sentencesListAdapter
                        it.data?.result?.let { it1 -> data.add(it1) }
                        sentencesListAdapter.setList(data)
                        stopDialog()
                    }
                    is NetworkResult.Error -> {
                        delay(2000)
                        stopDialog()
                        toast("Code:" + it.code + "--" + "Message:" + it.message)
                    }
                }
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}




