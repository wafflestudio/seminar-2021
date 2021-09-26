package com.wafflestudio.assignment2skeleton.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wafflestudio.assignment2skeleton.App
import com.wafflestudio.assignment2skeleton.model.Todo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // TODO : Complete ViewModel Class
    // Use todoRepository to save/load data from database

    private val todoRepository by lazy { (application as App).todoRepository }

    fun insertTodo(title: String, content: String) {
        viewModelScope.launch {
            todoRepository.insertTodo(Todo(title, content))
        }
    }
}
