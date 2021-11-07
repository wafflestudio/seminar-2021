package com.wafflestudio.assignment3skeleton.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wafflestudio.assignment3skeleton.App

// TODO
class DetailViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val memberRepository by lazy { (application as App).memberRepository }

    // TODO : Complete DetailViewModel

}
