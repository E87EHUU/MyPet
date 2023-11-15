package com.example.mypet.ui.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentFoodRecyclerItemBinding
import com.example.mypet.domain.food.FoodModel

class FoodAdapter(
    private val callback: FoodAdapterCallback,
) : ListAdapter<FoodModel, FoodAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentFoodRecyclerItemBinding.inflate(inflater, parent, false)
        return FoodAdapterViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: FoodAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<FoodModel>() {
        override fun areItemsTheSame(
            oldItem: FoodModel,
            newItem: FoodModel
        ) = oldItem.alarmId == newItem.alarmId

        override fun areContentsTheSame(
            oldItem: FoodModel,
            newItem: FoodModel
        ) = oldItem == newItem
    }
}