package com.example.mypet.ui.pet.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetCareRecyclerItemBinding
import com.example.mypet.domain.pet.care.PetCareModel

class PetCareAdapter(
    private val callback: PetCareAdapterCallback,
) : ListAdapter<PetCareModel, PetCareAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetCareAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetCareRecyclerItemBinding.inflate(inflater, parent, false)
        return PetCareAdapterViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetCareAdapterViewHolder, position: Int) {
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