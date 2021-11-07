package com.veldic.seminar2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.veldic.seminar2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var memoAdapter: MemoAdapter
    private lateinit var memoLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoAdapter = MemoAdapter()
        memoLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewMemo.apply {
            adapter = memoAdapter
            layoutManager = memoLayoutManager
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.addMemo(binding.editTextTitle.text.toString(), binding.editTextDetail.text.toString())
            binding.editTextTitle.setText("")
            binding.editTextDetail.setText("")
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteAll()
        }

        viewModel.observeMemo().observe(this, {
            memoAdapter.setMemos(it)
        })
    }
}