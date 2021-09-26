package com.veldic.seminar2.repository

import android.content.Context
import com.veldic.seminar2.db.MemoDao
import com.veldic.seminar2.db.MemoDatabase
import com.veldic.seminar2.model.Memo

class MemoRepository(private val memoDao: MemoDao) {

    fun getMemos() = memoDao.getAllMemos()
    suspend fun addMemo(memo: Memo) = memoDao.insertMemo(memo)
    suspend fun deleteAll() = memoDao.deleteAll()

    companion object {
        @Volatile
        private var INSTANCE: MemoRepository? = null

        @JvmStatic
        fun getInstance(memoDao: MemoDao): MemoRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MemoRepository(memoDao).also {
                INSTANCE = it
            }
        }
    }
}

