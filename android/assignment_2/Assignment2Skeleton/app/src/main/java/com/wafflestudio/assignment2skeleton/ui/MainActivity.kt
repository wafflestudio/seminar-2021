package com.wafflestudio.assignment2skeleton.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.wafflestudio.assignment2skeleton.databinding.ActivityMainBinding
import com.wafflestudio.assignment2skeleton.databinding.DialogAddTodoBinding

// Already Completed
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter()
        todoLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewTodo.apply {
            adapter = todoAdapter
            layoutManager = todoLayoutManager
        }

        binding.floatingButtonAddTodo.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialogBinding = DialogAddTodoBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Add Todos")
            .setView(dialogBinding.root)
            .setPositiveButton("create") { _, _ ->
                viewModel.insertTodo(
                    dialogBinding.textTitle.text.toString(),
                    dialogBinding.textContent.text.toString()
                )
                Toast.makeText(applicationContext, "Create", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("cancel") { _, _ ->
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
