package com.veldic.seminar2

import android.app.Application
import com.veldic.seminar2.db.MemoDatabase
import com.veldic.seminar2.repository.MemoRepository
import timber.log.Timber

class App : Application() {

    private val memoDatabase by lazy { MemoDatabase.getInstance(this) }
    val memoRepository by lazy { MemoRepository.getInstance(memoDatabase.memoDao()) }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

