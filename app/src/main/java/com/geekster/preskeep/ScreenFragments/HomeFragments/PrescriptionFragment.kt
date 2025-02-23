package com.geekster.preskeep.ScreenFragments.HomeFragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.HomeViewModels.PrescriptionViewModel
import com.geekster.preskeep.ViewModel.HomeViewModels.UserViewModel
import com.geekster.preskeep.databinding.FragmentPrescriptionBinding
import com.geekster.preskeep.databinding.FragmentUserBinding
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.NetworkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PrescriptionFragment : Fragment() {

    companion object {
        private const val STORAGE_PERMISSION_CODE = 100
    }

    private var _binding : FragmentPrescriptionBinding? = null

    private val binding get() = _binding!!

    private val prescriptionViewModel by viewModels<PrescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPrescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadFromDeviceBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                // Request the permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                // Permission has already been granted
                // Proceed with file retrieval
                openFilePicker()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
        filePickerLauncher.launch(intent)
    }

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                uri?.let {

                    lifecycleScope.launch {
                        prescriptionViewModel.uploadPrescription(it,requireContext())
                    }
                    bindUploadPrescriptionFile()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, proceed with file retrieval
                    openFilePicker()
                } else {
                    // Permission denied, handle accordingly
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun bindUploadPrescriptionFile(){
        prescriptionViewModel.prescriptionUploadLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResource.Success -> {
                    Toast.makeText(requireContext(), "Prescription uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                is NetworkResource.Error -> {
                    Toast.makeText(requireContext(), "Error uploading prescription, ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Prescription Error: ${it.message}")
                }
                is NetworkResource.Loading -> {
                    Log.d(TAG, "Prescription Loading: ")
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}