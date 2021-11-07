package com.wafflestudio.seminar3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.seminar3.databinding.ItemPostPurpleBinding
import com.wafflestudio.seminar3.databinding.ItemPostTealBinding
import com.wafflestudio.seminar3.model.Post

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var posts: List<Post> = listOf()

    inner class PostTealViewHolder(val binding: ItemPostTealBinding) : RecyclerView.ViewHolder(binding.root)
    inner class PostPurpleViewHolder(val binding: ItemPostPurpleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TEAL -> {
                val binding = ItemPostTealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostTealViewHolder(binding)
            }
            VIEW_TYPE_PURPLE -> {
                val binding = ItemPostPurpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostPurpleViewHolder(binding)
            }
            else -> throw IllegalStateException("viewType must be 0 or 1")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = posts[position]
        when (holder) {
            is PostTealViewHolder -> {
                holder.binding.apply {
                    textTitle.text = data.title
                    textBody.text = data.content
                }
            }
            is PostPurpleViewHolder -> {
                holder.binding.apply {
                    textTitlePurple.text = data.title
                    textBody.text = data.content
                }
            }
        }
    }

    override fun getItemCount() = posts.size

    override fun getItemViewType(position: Int): Int {
        return if (posts[position].id % 3 == 0) VIEW_TYPE_PURPLE else VIEW_TYPE_TEAL
    }


    fun postData(posts: List<Post>) {
        this.posts = posts
        this.notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_TEAL = 0
        const val VIEW_TYPE_PURPLE = 1
    }

}