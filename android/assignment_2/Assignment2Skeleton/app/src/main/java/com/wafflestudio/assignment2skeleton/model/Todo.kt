package com.wafflestudio.assignment2skeleton.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Already completed
@Entity(tableName = "todo_table")
data class Todo(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
