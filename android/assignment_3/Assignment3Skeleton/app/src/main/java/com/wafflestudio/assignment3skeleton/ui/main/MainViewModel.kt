package com.wafflestudio.assignment3skeleton.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wafflestudio.assignment3skeleton.App

// TODO
class MainViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val memberRepository by lazy { (application as App).memberRepository }

    // TODO : Complete MainViewModel

}
