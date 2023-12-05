package com.example.mypet.ui.care.main

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.domain.care.CareMainModel

class CareMainViewHolder(
    private val binding: FragmentCareRecyclerMainBinding,
    private val callback: CareMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careMainModel: CareMainModel


    fun bind(careMainModel: CareMainModel?) {
        careMainModel?.let {
            this.careMainModel = careMainModel

            binding.imageViewCareRecyclerMainIcon.setImageResource(careMainModel.careType.iconResId)
            binding.textViewCareRecyclerMainTitle.text =
                context.getString(careMainModel.careType.titleResId)

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }
}