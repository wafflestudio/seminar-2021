package com.wafflestudio.seminar3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wafflestudio.seminar3.App
import com.wafflestudio.seminar3.model.Post
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val postRepository by lazy { (application as App).postRepository }

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    fun fetchPostList() {
        viewModelScope.launch {
            try {
                val data = postRepository.getAllPost() // 통신이 실행
                _postList.value = data
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }
}


