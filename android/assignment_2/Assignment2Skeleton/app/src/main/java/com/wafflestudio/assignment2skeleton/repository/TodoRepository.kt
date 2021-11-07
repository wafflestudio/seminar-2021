package com.wafflestudio.assignment2skeleton.repository

import com.wafflestudio.assignment2skeleton.db.TodoDao
import com.wafflestudio.assignment2skeleton.model.Todo

class TodoRepository constructor(private val todoDao: TodoDao) {

    // TODO : Link Database and ViewModel using repository
    // ex) suspend fun deleteTodo(id: Long): ... , fun getAllTodos(): LiveData< ... >

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    companion object {
        @Volatile
        private var INSTANCE: TodoRepository? = null

        @JvmStatic
        fun getInstance(todoDao: TodoDao): TodoRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TodoRepository(todoDao).also { INSTANCE = it }
        }
    }
}