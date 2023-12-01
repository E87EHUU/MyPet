package com.example.mypet.ui.pet.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetCareRecyclerItemBinding
import com.example.mypet.domain.pet.care.PetCareModel

class PetCareAdapter(
    private val callback: PetCareCallback,
) : ListAdapter<PetCareModel, PetCareMainViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetCareMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetCareRecyclerItemBinding.inflate(inflater, parent, false)
        return PetCareMainViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetCareMainViewHolder, position: Int) {
        holder.bind(getItem(position))
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