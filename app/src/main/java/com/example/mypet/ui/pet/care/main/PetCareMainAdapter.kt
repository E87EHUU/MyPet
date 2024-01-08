package com.example.mypet.ui.pet.care.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareAddBinding
import com.example.mypet.app.databinding.FragmentPetRecyclerCareMainBinding
import com.example.mypet.domain.pet.care.PetCareModel

class PetCareMainAdapter(
    private val callback: PetCareMainCallback,
) : ListAdapter<PetCareModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemCount(): Int {
        return if (super.getItemCount() > 0) super.getItemCount() + 1 else 0
    }

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): RecyclerView.ViewHolder {
        return if (position == itemCount - 1) addViewHolder(parent)
        else mainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < itemCount - 1) (holder as PetCareMainViewHolder).bind(getItem(position))
    }

    private fun mainViewHolder(parent: ViewGroup): PetCareMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerCareMainBinding.inflate(inflater, parent, false)
        return PetCareMainViewHolder(binding, callback)
    }

    private fun addViewHolder(parent: ViewGroup): PetCareAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerCareAddBinding.inflate(inflater, parent, false)
        return PetCareAddViewHolder(binding, callback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<PetCareModel>() {
        override fun areItemsTheSame(
            oldItem: PetCareModel,
            newItem: PetCareModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PetCareModel,
            newItem: PetCareModel
        ) = oldItem == newItem
    }
}