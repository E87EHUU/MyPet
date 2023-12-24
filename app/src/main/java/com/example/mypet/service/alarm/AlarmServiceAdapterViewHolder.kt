package com.example.mypet.service.alarm

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.ServiceAlarmOverlayRecyclerItemBinding
import com.example.mypet.domain.alarm.service.AlarmServiceModel
import com.example.mypet.ui.getPetIcon

class AlarmServiceAdapterViewHolder(
    private val binding: ServiceAlarmOverlayRecyclerItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(localAlarmServiceModel: AlarmServiceModel) {
        with(localAlarmServiceModel) {
            if (petAvatarPath != null)
                binding.imageViewAlarmIcon.setImageURI(Uri.parse(petAvatarPath))
            else
                binding.imageViewAlarmIcon.setImageResource(getPetIcon(petKindOrdinal, petBreedOrdinal))

            //binding.textViewAlarmTitle.text = anyTitle
        }
    }
}