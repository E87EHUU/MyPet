package com.example.mypet.ui.pet.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetListRecyclerAddItemBinding
import com.example.mypet.app.databinding.FragmentPetListRecyclerItemBinding
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.ui.getPetIcon

class PetListAdapter(
    private val onPetClickListener: OnPetClickListener,
    private val onAddPetClickListener: OnAddPetClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var petList: List<PetModel> = arrayListOf()

    fun setPetList(petList: List<PetModel>) {
        this.petList = petList
        notifyDataSetChanged()
    }

    private enum class ViewType {
        ADD_PET,
        PET
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ADD_PET.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_pet_list_recycler_add_item, parent, false)
                AddPetViewHolder(view)
            }

            ViewType.PET.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_pet_list_recycler_item, parent, false)
                PetViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.ADD_PET.ordinal -> {
                val addPetHolder = holder as AddPetViewHolder
                addPetHolder.bind(onAddPetClickListener)
            }

            ViewType.PET.ordinal -> {
                val petHolder = holder as PetViewHolder
                if (position > 0 && position <= petList.size) {
                    val pet = petList[position - 1]
                    petHolder.bind(pet, onPetClickListener)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return petList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewType.ADD_PET.ordinal
            else -> ViewType.PET.ordinal
        }
    }

    inner class AddPetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(onAddPetClickListener: OnAddPetClickListener) {
            FragmentPetListRecyclerAddItemBinding.bind(itemView).apply {
                itemView.setOnClickListener { onAddPetClickListener.onAddPetClick() }
            }
        }
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(petModel: PetModel, onPetClickListener: OnPetClickListener) {
            FragmentPetListRecyclerItemBinding.bind(itemView).apply {
                if (petModel.avatarUri != null)
                    imageViewPetListItem.setImageURI(petModel.avatarUri)
                else
                    imageViewPetListItem.setImageResource(
                        getPetIcon(petModel.kindOrdinal, petModel.breedOrdinal)
                    )

                itemView.setOnClickListener { onPetClickListener.onPetClick(petModel) }
            }
        }
    }
}