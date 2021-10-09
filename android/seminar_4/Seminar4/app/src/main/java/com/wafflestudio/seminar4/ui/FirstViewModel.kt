package com.wafflestudio.seminar4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {

    private val text_ = MutableLiveData<String>("AAA")
    val text: LiveData<String> = text_

}