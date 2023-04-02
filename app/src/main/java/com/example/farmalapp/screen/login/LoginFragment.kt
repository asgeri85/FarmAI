package com.example.farmalapp.screen.login

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.farmalapp.MainActivity
import com.example.farmalapp.databinding.FragmentLoginBinding
import com.example.farmalapp.screen.soildetail.ApiStatus
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by lazy { LoginViewModel() }
    private var message: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        binding.fabLoginNavigation.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeLiveData() {
        with(loginViewModel) {
            status.observe(viewLifecycleOwner) {
                when (it) {
                    ApiStatus.DONE -> {
                        binding.progressBarLogin.visibility = View.GONE
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                    ApiStatus.LOADING -> {
                        binding.progressBarLogin.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.progressBarLogin.visibility = View.GONE
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            isLogin.observe(viewLifecycleOwner) {
                it?.let {
                    message = it
                }
            }
        }
    }

    private fun signIn() {
        val email = binding.editLoginEmail.text.toString().trim()
        val password = binding.editPasswordLogin.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginViewModel.login(email, password)
        } else {
            Toast.makeText(requireContext(), "Gerekli tüm alanları doldurunuz", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}