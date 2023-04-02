package com.example.farmalapp.screen.crops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.farmalapp.R
import com.example.farmalapp.databinding.FragmentCropsBinding
import com.example.farmalapp.model.CropResponseModel
import com.example.farmalapp.screen.soildetail.ApiStatus
import com.example.farmalapp.screen.soildetail.SoilDetailViewModel

class CropsFragment : Fragment() {

    private var _binding: FragmentCropsBinding? = null
    private val binding get() = _binding!!
    private val args: CropsFragmentArgs by navArgs()
    private lateinit var crops: List<String>
    private val cropAdapter by lazy { CropAdapter() }
    private val viewModel by lazy { SoilDetailViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCropsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvCrops.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvCrops.setHasFixedSize(true)
            rvCrops.adapter = cropAdapter

            val list= arrayListOf<String>()

            list.add("Apple")
            list.add("Pear")
            list.add("Peach")
            list.add("Corn")

            cropAdapter.updateList(list)

            floatingActionButton.setOnClickListener {
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.crops.observe(viewLifecycleOwner) {
            it?.let {
                crops = it.crops
                cropAdapter.updateList(crops)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.DONE -> binding.progressBarCrops.visibility = View.GONE
                ApiStatus.LOADING -> binding.progressBarCrops.visibility = View.VISIBLE
                else -> binding.progressBarCrops.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}