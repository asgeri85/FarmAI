package com.example.farmalapp.screen.soilInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmalapp.R
import com.example.farmalapp.databinding.FragmentSoilInfoBinding
import com.example.farmalapp.screen.soildetail.ApiStatus

class SoilInfoFragment : Fragment() {

    private var _binding: FragmentSoilInfoBinding? = null
    private val binding get() = _binding!!
    private val soilViewModel by lazy { SoilInfoViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSoilInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabInfoNavigate.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSoilInfo.setOnClickListener {
            uploadValueSoil()
        }
        //observeLiveData()
    }

    private fun uploadValueSoil() {
        with(binding) {
            val ph = editPhSoil.text.toString().trim()
            val nitrogen = editNitSoi.text.toString().trim()
            val phosphorus = editFosSoil.text.toString().trim()
            val potassium = editPotSoil.text.toString().trim()

            if (ph.isNotEmpty() && nitrogen.isNotEmpty() && phosphorus.isNotEmpty() && potassium.isNotEmpty()) {
               // soilViewModel.uploadValue(ph, nitrogen, phosphorus, potassium)
                findNavController().navigate(SoilInfoFragmentDirections.actionSoilInfoFragmentToCropsFragment(
                    1))
            } else {
                Toast.makeText(requireContext(), "Tüm alanları doldurunuz", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun observeLiveData() {
        soilViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.DONE -> {
                    binding.progressBarIInfo.visibility = View.GONE
                    findNavController().navigate(SoilInfoFragmentDirections.actionSoilInfoFragmentToCropsFragment(
                         1))
                }
                ApiStatus.LOADING -> binding.progressBarIInfo.visibility = View.VISIBLE
                else -> {
                    binding.progressBarIInfo.visibility = View.GONE
                    Toast.makeText(requireContext(), "Hata!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



