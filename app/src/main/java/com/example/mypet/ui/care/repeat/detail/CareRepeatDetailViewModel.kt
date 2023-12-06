package com.example.mypet.ui.care.repeat.detail

import androidx.lifecycle.ViewModel
import com.example.mypet.domain.care.repeat.CareRepeatDetailModel
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CareRepeatDetailViewModel @Inject constructor(

) : ViewModel() {
    var repeatTimes: String = REPEAT_TIMES_DEFAULT

    private lateinit var careRepeatDetailModel: CareRepeatDetailModel

    var timeInMillis: Long
        get() = careRepeatDetailModel.timeInMillis
        set(value) {
            careRepeatDetailModel.timeInMillis = value
        }

    var repeatIntervalOrdinal = CareRepeatInterval.DAY.ordinal

    init {
        careRepeatDetailModel = CareRepeatDetailModel()
    }

    companion object {
        const val REPEAT_TIMES_DEFAULT = "1"
    }
}