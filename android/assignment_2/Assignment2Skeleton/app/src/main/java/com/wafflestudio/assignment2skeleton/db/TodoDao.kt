package com.wafflestudio.assignment2skeleton.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wafflestudio.assignment2skeleton.model.Todo

@Dao
interface TodoDao {
    // TODO: Add queries(interaction) to match the assignment specification
    // Ex) - suspend fun deleteTodo ... fun getAllTodos ...
    // When you are not using LiveData, you have to use suspend fun
    // Read assignment specification to get how to use suspend fun

    @Insert
    suspend fun insertTodo(todo: Todo)
}
