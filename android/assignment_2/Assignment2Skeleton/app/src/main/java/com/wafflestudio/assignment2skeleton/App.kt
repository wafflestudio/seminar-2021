package com.wafflestudio.assignment2skeleton

import android.app.Application
import com.wafflestudio.assignment2skeleton.db.TodoDatabase
import com.wafflestudio.assignment2skeleton.repository.TodoRepository
import timber.log.Timber

class App : Application() {

    private val todoDatabase by lazy { TodoDatabase.getInstance(this) }
    val todoRepository by lazy { TodoRepository.getInstance(todoDatabase.todoDao()) }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
