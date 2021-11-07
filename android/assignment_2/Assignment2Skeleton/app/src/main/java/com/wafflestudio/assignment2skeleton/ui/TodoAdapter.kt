package com.wafflestudio.assignment2skeleton.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.assignment2skeleton.databinding.ItemTodoBinding

// TODO : Complete TodoAdapter
class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}
