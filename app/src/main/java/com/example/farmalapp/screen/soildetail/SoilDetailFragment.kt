package com.example.farmalapp.screen.soildetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.farmalapp.R
import com.example.farmalapp.databinding.FragmentSoilDetailBinding
import com.example.farmalapp.model.SoilResponseModel


class SoilDetailFragment : Fragment() {

    private var _binding: FragmentSoilDetailBinding? = null
    private val binding get() = _binding!!
    private val navArgs: SoilDetailFragmentArgs by navArgs()
    private val soilDetailViewModel by lazy { SoilDetailViewModel() }
    private lateinit var model: SoilResponseModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSoilDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (navArgs.type==2){
            binding.txtSoilNameDetail.text="Ashy"
            binding.txtSolidDetail1.text=getString(R.string.txt_detail)
            binding.btnSolidDetail.visibility=View.INVISIBLE
        }else{
            binding.txtSoilNameDetail.text="Silty Soil"
            binding.txtSolidDetail1.text=getString(R.string.detail_text)
            binding.btnSolidDetail.visibility=View.VISIBLE
        }

        binding.apply {
            fabNavigateDetailSoil.setOnClickListener {
                findNavController().popBackStack(R.id.homeFragment, false)
            }

            btnSolidDetail.setOnClickListener {
                findNavController().navigate(
                    SoilDetailFragmentDirections.actionSoilDetailFragmentToCropsFragment(0)
                )
            }

            imageSolidDetail.setImageBitmap(navArgs.bitmap.bitmap)
        }
        //observeLiveData()

    }

    private fun observeLiveData() {
        with(binding) {
            soilDetailViewModel.soilDetail.observe(viewLifecycleOwner) {
                it?.let {
                    model = it
                    txtSoilNameDetail.text = model.type
                    txtSolidDetail1.text = "• ${model.property}"
                }
            }

            soilDetailViewModel.status.observe(viewLifecycleOwner) {
                when (it) {
                    ApiStatus.DONE -> {
                        txtSolidDetail1.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        txtDetailError.visibility = View.GONE
                    }
                    ApiStatus.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        txtDetailError.visibility = View.VISIBLE
                        txtSolidDetail1.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}