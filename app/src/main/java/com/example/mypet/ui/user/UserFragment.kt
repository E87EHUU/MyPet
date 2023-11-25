package com.example.mypet.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentUserBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding by viewBinding(FragmentUserBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        with (binding) {
            profileAbout.lineImage.setImageResource(R.drawable.icon_about)
            profileAbout.lineText.text = getString(R.string.user_about)

            profilePrivacy.lineImage.setImageResource(R.drawable.icon_privacy)
            profilePrivacy.lineText.text = getString(R.string.user_privacy)

            profileSettings.lineImage.setImageResource(R.drawable.icon_settings)
            profileSettings.lineText.text = getString(R.string.user_settings)

            profileAbout.lineBlock.setOnClickListener {
                dialog(getString(R.string.user_about), getString(R.string.user_app_description))
            }
            profilePrivacy.lineBlock.setOnClickListener {
                dialog(getString(R.string.user_privacy), getString(R.string.user_privacy_description))
            }
            profileSettings.lineBlock.setOnClickListener {
                findNavController().navigate(UserFragmentDirections.actionUserFragmentToSettingsFragment())
            }
        }
    }

    private fun dialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.map_ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }
}