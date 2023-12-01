package com.example.mypet.ui.pet.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerAddBinding
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerMainBinding
import com.example.mypet.domain.pet.PetListModel

class PetListAdapter(
    private val callback: PetListCallback,
) : ListAdapter<PetListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = super.getItemCount() + 1

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return if (position == 0) addViewHolder(parent)
        else mainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) (holder as PetListAddViewHolder)
        else (holder as PetListMainViewHolder).bind(getItem(position - 1))
    }

    private fun mainViewHolder(parent: ViewGroup): PetListMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerMainRecyclerMainBinding.inflate(inflater, parent, false)
        return PetListMainViewHolder(binding, callback)
    }

    private fun addViewHolder(parent: ViewGroup): PetListAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerMainRecyclerAddBinding.inflate(inflater, parent, false)
        return PetListAddViewHolder(binding, callback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<PetListModel>() {
        override fun areItemsTheSame(
            oldItem: PetListModel,
            newItem: PetListModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PetListModel,
            newItem: PetListModel
        ) = oldItem == newItem
    }
}