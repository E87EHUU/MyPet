package com.example.mypet.ui.user

import android.os.Bundle
import android.text.Spanned
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.BuildConfig
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

            profilePreferences.lineImage.setImageResource(R.drawable.icon_settings)
            profilePreferences.lineText.text = getString(R.string.user_settings)

            profileAbout.lineBlock.setOnClickListener {
                dialog(getString(R.string.user_about), getAppVersion())
            }
            profilePrivacy.lineBlock.setOnClickListener {
                dialog(getString(R.string.user_privacy), getPrivacyPolicy())
            }
            profilePreferences.lineBlock.setOnClickListener {
                findNavController().navigate(UserFragmentDirections.actionUserFragmentToPreferencesFragment())
            }
        }
    }

    private fun getAppVersion() =
        """
            ${getString(R.string.app_name)}
            
            ${getString(R.string.user_version_name)}: ${BuildConfig.VERSION_NAME}
            ${getString(R.string.user_version_code)}: ${BuildConfig.VERSION_CODE}
        """.trimIndent().toSpannable()

    private fun getPrivacyPolicy(): Spanned {
        val text = getString(R.string.user_privacy_description)
        val dynamicText = String.format(text, null)
        return HtmlCompat.fromHtml(dynamicText, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun dialog(title: String, message: Spanned) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.map_ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }
}