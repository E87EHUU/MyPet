package com.example.mypet.ui.care.note

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerNoteBinding
import com.example.mypet.domain.care.CareNoteModel

class CareNoteViewHolder(
    private val binding: FragmentCareRecyclerNoteBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var careNoteModel: CareNoteModel? = null

    init {
        with(binding) {
            textInputEditTextCareRecyclerNote.doOnTextChanged { text, _, _, _ ->
                careNoteModel?.let {
                    it.note = text.toString()
                }
            }
        }
    }

    fun bind(careNoteModel: CareNoteModel) {
        this.careNoteModel = careNoteModel
        binding.textInputEditTextCareRecyclerNote.setText(careNoteModel.note)
    }
}