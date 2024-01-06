package com.example.mypet.ui.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentLoginBinding
import com.example.mypet.ui.clear
import com.example.mypet.ui.getToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onStop() {
        super.onStop()
        getToolbar()?.clear()
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }

                binding.buttonSignIn.isEnabled = loginFormState.isDataValid
                binding.buttonCreateUser.isEnabled = loginFormState.isDataValid

                loginFormState.emailError?.let {
                    binding.email.error = getString(it)
                }

                loginFormState.passwordError?.let {
                    binding.password.error = getString(it)
                }
            })

        loginViewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.loading.visibility = View.VISIBLE

                is AuthResult.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.exception.message.toString(),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                is AuthResult.Success -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), R.string.login_welcome, Toast.LENGTH_LONG)
                        .show()
                    with(binding) {
                        buttonSignIn.visibility = View.GONE
                        buttonCreateUser.visibility = View.GONE
                        emailLayout.visibility = View.GONE
                        passwordLayout.visibility = View.GONE
                        buttonSignOut.visibility = View.VISIBLE
                    }
                }

                is AuthResult.SuccessOut -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), R.string.login_signed_out, Toast.LENGTH_LONG)
                        .show()
                    with(binding) {
                        buttonSignIn.visibility = View.VISIBLE
                        buttonCreateUser.visibility = View.VISIBLE
                        emailLayout.visibility = View.VISIBLE
                        passwordLayout.visibility = View.VISIBLE
                        buttonSignOut.visibility = View.GONE
                    }
                }

            }
        }
    }

    private fun initView() {
        with(binding) {

            val afterTextChangedListener = object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    loginViewModel.loginDataChanged(
                        email.text.toString(),
                        password.text.toString()
                    )
                }
            }

            email.addTextChangedListener(afterTextChangedListener)
            password.addTextChangedListener(afterTextChangedListener)

            buttonSignIn.setOnClickListener {
                loginViewModel.sendCredentialsForSignIn(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            buttonCreateUser.setOnClickListener {
                loginViewModel.sendCredentialsForCreateUser(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            buttonSignOut.setOnClickListener {
                loginViewModel.logoutCurrentUser()
            }
        }
    }

    private fun initToolbar() {
        getToolbar()
            ?.clear()
            ?.let { toolbar ->
                toolbar.title = getString(R.string.login_toolbar_title)
            }
    }

}