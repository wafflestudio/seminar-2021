package com.veldic.seminar2.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.veldic.seminar2.model.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM memos")
    fun getAllMemos(): LiveData<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("DELETE FROM memos WHERE id=:id")
    suspend fun deleteMemoById(id: Int)

    @Query("DELETE FROM memos")
    suspend fun deleteAll()
}
