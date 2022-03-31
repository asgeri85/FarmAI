package com.example.farmalapp.screen.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmalapp.R
import com.example.farmalapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSplashLogin.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }

        binding.btnSplashRegister.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}