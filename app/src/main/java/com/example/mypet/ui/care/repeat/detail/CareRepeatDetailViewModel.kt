package com.example.mypet.ui.care.repeat.detail

import androidx.lifecycle.ViewModel
import com.example.mypet.domain.care.repeat.CareRepeatDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CareRepeatDetailViewModel @Inject constructor(

) : ViewModel() {
    val detail = CareRepeatDetailModel()

}