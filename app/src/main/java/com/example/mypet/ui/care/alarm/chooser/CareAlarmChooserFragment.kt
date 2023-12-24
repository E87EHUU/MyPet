package com.example.mypet.ui.care.alarm.chooser

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R

class CareAlarmChooserFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = requireActivity().layoutInflater.inflate(
            R.layout.fragment_care_alarm_chooser, null
        ) as RecyclerView
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.food_interval)
        )

        root.adapter

        return super.onCreateDialog(savedInstanceState)
    }
}