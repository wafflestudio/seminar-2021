package com.wafflestudio.seminar3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wafflestudio.seminar3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var postAdapter: PostAdapter
    private lateinit var postLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postAdapter = PostAdapter()
        postLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewPost.apply {
            adapter = postAdapter
            layoutManager = postLayoutManager
        }

        viewModel.fetchPostList()

        viewModel.postList.observe(this) {
            postAdapter.postData(it)
        }
    }
}