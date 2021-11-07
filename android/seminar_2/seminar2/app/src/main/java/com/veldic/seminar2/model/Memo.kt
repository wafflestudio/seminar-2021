package com.veldic.seminar2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
data class Memo(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "detail")
    val detail: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
