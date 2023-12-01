package com.example.mypet.ui.pet

import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetRecyclerMainBinding
import com.example.mypet.domain.pet.PetListModel
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getPetName
import com.example.mypet.ui.pet.main.PetMainCallback
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
        binding.recyclerViewPetRecyclerMain.adapter = petListAdapter
        initMenuPetAction()
    }

    private fun initMenuPetAction() {
        binding.buttonPetRecyclerMainMore.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.pet_action_menu, popupMenu.menu)

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

    fun bind(petListModels: List<PetListModel>) {
        this.petListModels = petListModels
        petListAdapter.submitList(petListModels)

        onClickPet(petListModels.firstOrNull())
    }

    private fun updatePet(petListModel: PetListModel?) {
        petListModel?.let {
            activePetListModel = petListModel

            if (it.avatarUri != null) {
                Glide.with(context)
                    .load(it.avatarUri)
                    .circleCrop()
                    .into(binding.imageViewPetRecyclerMainAvatarIcon)
            } else
                binding.imageViewPetRecyclerMainAvatarIcon
                    .setImageResource(getPetIcon(it.kindOrdinal, it.breedOrdinal))

            binding.textViewPetRecyclerMainName.text = it.name
            binding.textViewPetRecyclerMainBreedName.text =
                context.getString(getPetName(it.kindOrdinal, it.breedOrdinal))

            binding.imageViewPetRecyclerMainEmpty.isVisible = false
            binding.groupPetRecyclerMain.isVisible = true
        } ?: run {
            binding.groupPetRecyclerMain.isVisible = false
            binding.imageViewPetRecyclerMainEmpty.isVisible = true
        }

        petListModel?.age?.let {
            binding.textViewPetRecyclerMainAgeText.text = it
            binding.materialCardViewPetRecyclerMainAge.isVisible = true
        } ?: run {
            binding.materialCardViewPetRecyclerMainAge.isVisible = false
        }

        petListModel?.weight?.let {
            binding.textViewPetRecyclerMainWeightText.text = it
            binding.materialCardViewPetRecyclerMainWeight.isVisible = true
        } ?: run {
            binding.materialCardViewPetRecyclerMainWeight.isVisible = false
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