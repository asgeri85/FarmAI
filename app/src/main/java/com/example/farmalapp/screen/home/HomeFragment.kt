package com.example.farmalapp.screen.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmalapp.LoginActivity
import com.example.farmalapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance().currentUser

        /*if (auth == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHomePhoto.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPhotoFragment(1))
        }

        binding.btnHomeWeather.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLocationFragment())
        }

        binding.btnHomePlant.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPhotoFragment(2))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}