package com.veldic.seminar2.ui

import android.app.Application
import androidx.lifecycle.*
import com.veldic.seminar2.App
import com.veldic.seminar2.model.Memo
import kotlinx.coroutines.launch
import timber.log.Timber


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val memoRepository by lazy { (application as App).memoRepository }

    fun observeMemo() = memoRepository.getMemos()

    fun addMemo(title: String, detail: String) {
        viewModelScope.launch {
            memoRepository.addMemo(Memo(title, detail))
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            memoRepository.deleteAll()
        }
    }
}