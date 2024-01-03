package com.example.mypet.domain.pet.list

private const val PET_LIST_ADD_ID = -1

sealed class PetListModel(open val id: Int)

data class PetListMainModel(
    override val id: Int,
    val avatarUri: String?,
    val name: String,
    val dateOfBirth: Long?,
    val weight: Float?,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val sex: Int?,
    var isActive: Boolean,
) : PetListModel(id)

data object PetListAddModel : PetListModel(PET_LIST_ADD_ID)