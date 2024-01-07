package com.example.mypet.ui.pet.main

import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetRecyclerMainBinding
import com.example.mypet.domain.pet.PetSex
import com.example.mypet.domain.pet.list.PetListAddModel
import com.example.mypet.domain.pet.list.PetListMainModel
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
    private var petListMainModels: List<PetListMainModel>? = null
    private val petListAdapter: PetListAdapter =
        PetListAdapter(this)
    private var activePetListMainModel: PetListMainModel? = null

    init {
        binding.imageViewPetRecyclerMainAvatar.setOnClickListener {
            activePetListMainModel?.let { callback.onClickPetEdit(it) }
        }
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
                        activePetListMainModel?.let { callback.onClickPetEdit(it) }
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
        activePetListMainModel?.let { petListModel ->
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

    fun bind(petListMainModels: List<PetListMainModel>?) {
        this.petListMainModels = petListMainModels

        updateUIActivePetInPetList()
        onClickPet(findActivePetListModel())
    }

    private fun findActivePetListModel(): PetListMainModel? {
        petListMainModels?.let { petListModels ->
            for (petListModel in petListModels) {
                if (petListModel.isActive) return petListModel
            }
            return petListModels.firstOrNull()
        }

        return null
    }

    private fun updatePet(petListMainModel: PetListMainModel?) {
        petListMainModel?.let {
            with(binding) {
                Glide.with(itemView)
                    .load(petListMainModel.avatarUri)
                    .circleCrop()
                    .placeholder(
                        getPetIcon(
                            petListMainModel.kindOrdinal,
                            petListMainModel.breedOrdinal
                        )
                    )
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageViewPetRecyclerMainAvatarIcon)

                textViewPetRecyclerMainName.text = petListMainModel.name
                textViewPetRecyclerMainBreedName.text =
                    context.getString(
                        getPetName(petListMainModel.kindOrdinal, petListMainModel.breedOrdinal)
                    )

                petListMainModel.dateOfBirth?.let {
                    textViewPetRecyclerMainAgeText.text = getPetsAge(it)
                    linearLayoutPetRecyclerMainAge.isVisible = true
                } ?: run {
                    linearLayoutPetRecyclerMainAge.isVisible = false
                }

                petListMainModel.weight?.let {
                    textViewPetRecyclerMainWeightText.text = it.toString()
                    linearLayoutPetRecyclerMainWeight.isVisible = true
                } ?: run {
                    linearLayoutPetRecyclerMainWeight.isVisible = false
                }

                when (petListMainModel.sex) {
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

                imageViewPetRecyclerMainEmpty.isVisible = false
                groupPetRecyclerMain.isVisible = true
            }

            updateUIActivePetInPetList(petListMainModel)
        } ?: run {
            binding.groupPetRecyclerMain.isVisible = false
            binding.imageViewPetRecyclerMainSexMale.isVisible = false
            binding.imageViewPetRecyclerMainSexFemale.isVisible = false
            binding.linearLayoutPetRecyclerMainWeight.isVisible = false
            binding.linearLayoutPetRecyclerMainAge.isVisible = false

            binding.imageViewPetRecyclerMainEmpty.isVisible = true
        }
    }

    private fun updateUIActivePetInPetList(activePetListMainModel: PetListMainModel? = null) {
        val mutablePetListModels = mutableListOf<PetListModel>()
        mutablePetListModels.add(PetListAddModel)

        petListMainModels?.let { petListMainModels ->
            val updatedPetListMainModels = petListMainModels.map { petListModel ->
                if (petListModel.isActive) {
                    petListModel.isActive = false
                    petListModel
                } else if (petListModel == activePetListMainModel) {
                    val newActivePetListModel =
                        petListModel.copy(isActive = petListModel == activePetListMainModel)
                    this.activePetListMainModel = newActivePetListModel
                    newActivePetListModel
                } else {
                    petListModel
                }
            }
            mutablePetListModels.addAll(updatedPetListMainModels)
        }

        petListAdapter.submitList(mutablePetListModels)
    }

    override fun onClickPetAdd() {
        callback.onClickPetAdd()
    }

    override fun onClickPet(petListMainModel: PetListMainModel?) {
        if (activePetListMainModel != petListMainModel) {
            updatePet(petListMainModel)
            callback.onClickPet(petListMainModel)
        }
    }
}