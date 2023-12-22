package com.example.mypet.ui.pet.main

import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetRecyclerMainBinding
import com.example.mypet.domain.pet.PetSex
import com.example.mypet.domain.pet.list.PetListModel
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getPetName
import com.example.mypet.ui.getPetsAge
import com.example.mypet.ui.pet.main.list.PetListAdapter
import com.example.mypet.ui.pet.main.list.PetListCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PetMainViewHolder(
    private val binding: FragmentPetRecyclerMainBinding,
    private val callback: PetMainCallback,
) : RecyclerView.ViewHolder(binding.root), PetListCallback {
    private val context = binding.root.context
    private lateinit var petListModels: List<PetListModel>
    private val petListAdapter: PetListAdapter =
        PetListAdapter(this)
    private var activePetListModel: PetListModel? = null

    init {
        binding.recyclerViewPetRecyclerMain.itemAnimator = null
        binding.recyclerViewPetRecyclerMain.adapter = petListAdapter
        initMenuPetAction()
    }

    private fun initMenuPetAction() {
        binding.buttonPetRecyclerMainMore.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.popup_pet_action_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.pet_menu_item_edit_pet -> {
                        activePetListModel?.let { callback.onClickPetEdit(it) }
                        true
                    }

                    R.id.pet_menu_item_delete_pet -> {
                        showDeletePetDialog()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun showDeletePetDialog() {
        activePetListModel?.let { petListModel ->
            val deletePetAlertDialog =
                LayoutInflater.from(context).inflate(R.layout.alert_dialog_delete_pet, null)

            val alertDialog = MaterialAlertDialogBuilder(context)
                .setView(deletePetAlertDialog)
                .create()

            deletePetAlertDialog.findViewById<Button>(R.id.buttonAcceptDeletePetDialog)
                .setOnClickListener {
                    alertDialog.dismiss()
                    callback.onClickPetDelete(petListModel)
                }

            deletePetAlertDialog.findViewById<Button>(R.id.buttonCancelDeletePetDialog)
                .setOnClickListener { alertDialog.dismiss() }

            alertDialog.show()
        }
    }

    fun bind(petListModels: List<PetListModel>?, activePetListId: Int?) {
        petListModels?.let {
            this.petListModels = petListModels
            petListAdapter.submitList(petListModels)
            onClickPet(petListModels.find { it.id == activePetListId }
                ?: petListModels.firstOrNull())
        }
    }

    private fun updatePet(petListModel: PetListModel?) {
        petListModel?.let {
            activePetListModel = petListModel

            with(binding) {
                Glide.with(itemView)
                    .load(petListModel.avatarUri)
                    .circleCrop()
                    .placeholder(getPetIcon(petListModel.kindOrdinal, petListModel.breedOrdinal))
                    .into(binding.imageViewPetRecyclerMainAvatarIcon)

                textViewPetRecyclerMainName.text = petListModel.name
                textViewPetRecyclerMainBreedName.text =
                    context.getString(
                        getPetName(petListModel.kindOrdinal, petListModel.breedOrdinal)
                    )

                petListModel.dateOfBirth?.let {
                    binding.textViewPetRecyclerMainAgeText.text = getPetsAge(it)
                    binding.linearLayoutPetRecyclerMainAge.isVisible = true
                } ?: run {
                    binding.linearLayoutPetRecyclerMainAge.isVisible = false
                }

                petListModel.weight?.let {
                    binding.textViewPetRecyclerMainWeightText.text = it.toString()
                    binding.linearLayoutPetRecyclerMainWeight.isVisible = true
                } ?: run {
                    binding.linearLayoutPetRecyclerMainWeight.isVisible = false
                }

                petListModel.sex?.let {
                    when (it) {
                        PetSex.MALE.ordinal -> {
                            imageViewPetRecyclerMainSexMale.isVisible = true
                            imageViewPetRecyclerMainSexFemale.isVisible = false
                        }

                        PetSex.FEMALE.ordinal -> {
                            imageViewPetRecyclerMainSexMale.isVisible = false
                            imageViewPetRecyclerMainSexFemale.isVisible = true
                        }

                        else -> {
                            imageViewPetRecyclerMainSexMale.isVisible = false
                            imageViewPetRecyclerMainSexFemale.isVisible = false
                        }
                    }
                }

                imageViewPetRecyclerMainEmpty.isVisible = false
                groupPetRecyclerMain.isVisible = true
            }
        } ?: run {
            binding.groupPetRecyclerMain.isVisible = false
            binding.imageViewPetRecyclerMainSexMale.isVisible = false
            binding.imageViewPetRecyclerMainSexFemale.isVisible = false
            binding.linearLayoutPetRecyclerMainWeight.isVisible = false
            binding.linearLayoutPetRecyclerMainAge.isVisible = false

            binding.imageViewPetRecyclerMainEmpty.isVisible = true
        }
    }

    override fun onClickPetAdd() {
        callback.onClickPetAdd()
    }

    override fun onClickPet(petListModel: PetListModel?) {
        callback.onClickPet(petListModel)
        updatePet(petListModel)
    }
}