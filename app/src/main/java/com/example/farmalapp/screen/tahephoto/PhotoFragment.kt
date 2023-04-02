package com.example.farmalapp.screen.tahephoto

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.farmalapp.R
import com.example.farmalapp.databinding.FragmentPhotoBinding
import com.example.farmalapp.model.PhotoModel
import com.example.farmalapp.screen.soildetail.ApiStatus
import java.io.ByteArrayOutputStream

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!
    private val photoViewModel by lazy { PhotoViewModel() }
    private lateinit var phm: PhotoModel
    private val args:PhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhotoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()

        if (args.type==2){
            binding.textView2.text=getString(R.string.photo_title)
            binding.btnVareblePhoto.visibility=View.INVISIBLE
        }else{
            binding.textView2.text=getString(R.string.txt_camera_title)
            binding.btnVareblePhoto.visibility=View.VISIBLE
        }

        binding.apply {

            btnCameraPhoto.setOnClickListener {
                dispatchTakePictureIntent()
            }

            fabNavigatePhoto.setOnClickListener {
                findNavController().popBackStack()
            }

            btnVareblePhoto.setOnClickListener {
                findNavController().navigate(PhotoFragmentDirections.actionPhotoFragmentToSoilInfoFragment())
            }
        }
    }

    private fun observeLiveData() {
        photoViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.DONE -> {
                    binding.progressBarPhoto.visibility = View.GONE
                    findNavController().navigate(PhotoFragmentDirections.actionPhotoFragmentToSoilDetailFragment(
                        phm,args.type))
                }
                ApiStatus.LOADING -> binding.progressBarPhoto.visibility = View.VISIBLE
                else -> {
                    binding.progressBarPhoto.visibility = View.GONE
                    Toast.makeText(requireContext(), "Hata!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, 1)
        } catch (e: ActivityNotFoundException) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            phm = PhotoModel(imageBitmap)
            imageBitmap.let {
                val uri = getImageUriFromBitmap(requireContext(), imageBitmap)
                findNavController().navigate(PhotoFragmentDirections.actionPhotoFragmentToSoilDetailFragment(
                    phm,args.type))
                // photoViewModel.uploadImage(uri)
            }
        }
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}