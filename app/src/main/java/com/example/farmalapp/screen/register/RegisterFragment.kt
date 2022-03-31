package com.example.farmalapp.screen.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmalapp.MainActivity
import com.example.farmalapp.databinding.FragmentRegisterBinding
import com.example.farmalapp.screen.login.LoginViewModel
import com.example.farmalapp.screen.soildetail.ApiStatus

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel by lazy { LoginViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()

        binding.btnRegister.setOnClickListener {
            signUp()
        }

        binding.fabNavigationRegister.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUp() {
        val email = binding.editEmailRegister.text.toString().trim()
        val password = binding.editPassRegister.text.toString().trim()
        val password2 = binding.editPass2Register.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {
            if (password == password2) {
                registerViewModel.register(email, password)
            } else {
                Toast.makeText(requireContext(),
                    "Şifreler aynı değil.Lütfen kontrol ediniz",
                    Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), "Gerekli tüm alanları doldurunuz", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun observeLiveData() {
        var message: String? = null

        registerViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.DONE -> {
                    binding.progressBarRegister.visibility = View.GONE
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
                ApiStatus.LOADING -> binding.progressBarRegister.visibility = View.VISIBLE
                else -> {
                    binding.progressBarRegister.visibility = View.GONE
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
            }
        }

        registerViewModel.isLogin.observe(viewLifecycleOwner) {
            it?.let {
                message = it
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}