package com.example.mypet.domain.care

data class CareSaveModel(
    val main: CareMainModel,
    val start: CareStartModel?,
    val repeat: CareRepeatModel?,
    val end: CareEndModel?,
    val alarm: CareAlarmModel?,
    val note: CareNoteModel?,
)