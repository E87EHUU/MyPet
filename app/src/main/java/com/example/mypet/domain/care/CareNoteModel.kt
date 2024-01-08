package com.example.mypet.domain.care

data class CareNoteModel(
    val id: Int,
    var note: String? = null,
) : CareModel(CARE_ADAPTER_NOTE_KEY)