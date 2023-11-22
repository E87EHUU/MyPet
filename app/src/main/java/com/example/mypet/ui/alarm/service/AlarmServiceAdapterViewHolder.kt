package com.example.mypet.ui.alarm.service

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.ServiceAlarmOverlayRecyclerItemBinding
import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel
import com.example.mypet.ui.getPetIcon

class AlarmServiceAdapterViewHolder(
    private val binding: ServiceAlarmOverlayRecyclerItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(localAlarmServiceModel: LocalAlarmServiceModel) {
        with(localAlarmServiceModel) {
            if (anyAvatarPath != null)
                binding.imageViewAlarmIcon.setImageURI(Uri.parse(anyAvatarPath))
            else
                binding.imageViewAlarmIcon.setImageResource(getPetIcon(kindOrdinal, breedOrdinal))

            binding.textViewAlarmTitle.text = anyTitle
        }
    }
}