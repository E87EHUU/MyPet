package com.example.mypet.ui.pet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareBinding
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodBinding
import com.example.mypet.app.databinding.FragmentPetRecyclerMainBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.domain.pet.PetModel
import com.example.mypet.ui.pet.care.PetCareCallback
import com.example.mypet.ui.pet.food.PetFoodCallback
import com.example.mypet.ui.pet.main.PetMainCallback

class PetAdapter(
    private val petMainCallback: PetMainCallback,
    private val petFoodCallback: PetFoodCallback,
    private val petCareCallback: PetCareCallback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount() = 3

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): RecyclerView.ViewHolder {
        return when (position) {
            PET_POSITION -> mainViewHolder(parent)
            FOOD_POSITION -> foodViewHolder(parent)
            else -> careViewHolder(parent)
        }
    }

    var petListModel: List<PetModel> = emptyList()
    var food: List<PetFoodAlarmModel> = emptyList()
    var care: List<PetCareModel> = emptyList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            PET_POSITION -> (holder as PetMainViewHolder).bind(petListModel)
            FOOD_POSITION -> (holder as PetFoodViewHolder).bind(food)
            else -> (holder as PetCareViewHolder).bind(care)
        }
    }

    private fun mainViewHolder(parent: ViewGroup): PetMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerMainBinding.inflate(inflater, parent, false)
        return PetMainViewHolder(binding, petMainCallback)
    }

    private fun foodViewHolder(parent: ViewGroup): PetFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerFoodBinding.inflate(inflater, parent, false)
        return PetFoodViewHolder(binding, petFoodCallback)
    }

    private fun careViewHolder(parent: ViewGroup): PetCareViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerCareBinding.inflate(inflater, parent, false)
        return PetCareViewHolder(binding, petCareCallback)
    }

    companion object {
        const val PET_POSITION = 0
        const val FOOD_POSITION = 1
        const val CARE_POSITION = 2
    }
}