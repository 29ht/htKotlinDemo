package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.Sentences
import com.example.htkotlinmvvmdemo1.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SentencesViewModel(application: Application) : AndroidViewModel(application) {
    var dailyVerse: MutableStateFlow<NetworkResult<Sentences>> = MutableStateFlow(NetworkResult.Loading())

    fun getBaiduImage() {
        viewModelScope.launch {
            MenuRepository.getDailyVerse().collect {
                dailyVerse.emit(it)
            }
        }
    }

}