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
import com.example.mypet.ui.pet.main.list.PetListAdapter
import com.example.mypet.ui.pet.main.list.PetListCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDate
import java.time.Period
import java.util.Locale


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
            updateUIActivePetInPetList()

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

    private fun getPetsAge(timeMillis: Long): String {
        val birthDate =
            LocalDate.ofEpochDay(timeMillis / (24 * 60 * 60 * 1000)).atStartOfDay().toLocalDate()
        val currentDate = LocalDate.now()

        val period = Period.between(birthDate, currentDate)

        val years = period.years
        val months = period.months
        val days = period.days

        return when {
            years == 0 && months == 0 -> getDaysString(days)
            months < 0 -> {
                val correctedYears = years - 1
                val correctedMonths = months + 12
                getAgeString(correctedYears, correctedMonths)
            }
            else -> getAgeString(years, months)
        }
    }

    private fun getDaysString(days: Int): String {
        if (Locale.getDefault().displayLanguage.lowercase() == LOCALE_RU) {
            return when {
                days == 0 -> context.getString(R.string.pet_age_today)
                days % 10 == 1 && days % 100 != 11 -> "$days ${context.getString(R.string.pet_age_day)}"
                days % 10 in 2..4 && days % 100 !in 12..14 -> "$days ${context.getString(R.string.pet_age_days_dnya)}"
                else -> "$days ${context.getString(R.string.pet_age_days_dney)}"
            }
        } else if (Locale.getDefault().displayLanguage.lowercase() == LOCALE_EN) {
            return when (days) {
                0 -> context.getString(R.string.pet_age_today)
                1 -> "$days ${context.getString(R.string.pet_age_day)}"
                else -> "$days ${context.getString(R.string.pet_age_days_dney)}"
            }
        } else {
            return ""
        }
    }

    private fun getAgeString(years: Int, months: Int): String {
        var yearsString = ""
        var monthsString =""

        if (Locale.getDefault().displayLanguage.lowercase() == LOCALE_RU) {
            yearsString = when {
                years == 0 -> ""
                years % 10 == 1 && years != 11 -> "$years ${context.getString(R.string.pet_age_year)}"
                years % 10 in 2..4 && (years % 100 < 10 || years % 100 >= 20) -> "$years ${context.getString(R.string.pet_age_years_goda)}"
                else -> "$years ${context.getString(R.string.pet_age_years_let)}"
            }

            monthsString = when (months) {
                0 -> ""
                1 -> "$months ${context.getString(R.string.pet_age_month)}"
                in 2..4 -> "$months ${context.getString(R.string.pet_age_months_mesyaca)}"
                else -> "$months ${context.getString(R.string.pet_age_months_mesyacev)}"
            }
        } else if (Locale.getDefault().displayLanguage.lowercase() == LOCALE_EN) {
            yearsString = when (years) {
                0 -> ""
                1 -> "$years ${context.getString(R.string.pet_age_year)}"
                else -> "$years ${context.getString(R.string.pet_age_years_let)}"
            }

            monthsString = when (months) {
                0 -> ""
                1 -> "$months ${context.getString(R.string.pet_age_month)}"
                else -> "$months ${context.getString(R.string.pet_age_months_mesyacev)}"
            }
        }

        return "$yearsString$monthsString"
    }

    companion object {
        const val LOCALE_RU = "русский"
        const val LOCALE_EN = "english"
    }
}