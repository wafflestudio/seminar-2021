package com.veldic.seminar2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.veldic.seminar2.databinding.ItemMemoBinding
import com.veldic.seminar2.model.Memo
import timber.log.Timber

class MemoAdapter : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private var memos: List<Memo> = listOf()

    inner class MemoViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val data = memos[position]
            holder.binding.apply {
                textTitle.text = data.title
                textDetail.text = data.detail
        }
    }

    override fun getItemCount() = memos.size

    fun setMemos(memos: List<Memo>) {
        Timber.d(memos.toString())

        this.memos = memos
        this.notifyDataSetChanged()
    }
}

