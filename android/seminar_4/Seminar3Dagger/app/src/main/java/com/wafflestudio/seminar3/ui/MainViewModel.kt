package com.wafflestudio.seminar3.ui

import android.app.Application
import androidx.lifecycle.*
import com.wafflestudio.seminar3.App
import com.wafflestudio.seminar3.model.Post
import com.wafflestudio.seminar3.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

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


