package com.geekster.preskeep.ScreenFragments.HomeFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.HomeViewModels.UserViewModel
import com.geekster.preskeep.databinding.FragmentUserBinding
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.NetworkResource
import com.geekster.preskeep.utils.TokenManager
import com.qamar.curvedbottomnaviagtion.log
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.extensions.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding : FragmentUserBinding? = null

    private val binding get() = _binding!!

    private val userViewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAvatarObserver()
        bindInfoObserver()

        lifecycleScope.launch {
            userViewModel.getUserInfo(tokenManager.getToken("DOCUMENT_ID").toString())
        }

    }

    private fun bindInfoObserver() {
        userViewModel.userInfoLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResource.Success -> {
                    Log.d(TAG, "UserFragment: $it")
                    lifecycleScope.launch {
                        userViewModel.getAvatar(it.data?.data?.get("Name").toString())
                    }
                    binding.name.text = it.data?.data?.get("Name").toString()
                    binding.profileDob.text = it.data?.data?.get("Gender").toString()
                    binding.profilePhone.text = it.data?.data?.get("Phone").toString()

                    //Prescription as List
                    val listOfPrescription  = it.data?.data?.get("Prescriptions").let { random ->
                        random as List<*>
                    }
                    binding.prescriptionNo.text = listOfPrescription.size.toString()
                    Log.d(TAG, "Prescription List : ${listOfPrescription.size}")

                    //Report as List
                    val listOfReports  = it.data?.data?.get("Reports").let { random2 ->
                        random2 as List<*>
                    }
                    binding.reportNo.text = listOfReports.size.toString()
                    Log.d(TAG, "Report List : ${listOfReports.size}")

                }
                is NetworkResource.Error -> {
                    Log.d(TAG, "Error in Document: ${it.message}, ${tokenManager.getToken("DOCUMENT_ID")}")
                }
                is NetworkResource.Loading -> {
                    Log.d(TAG, "Loading... ")
                }
            }
        }
    }

    private fun bindAvatarObserver(){
        userViewModel.avatarLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResource.Success -> {
                    binding.profileImage.load(it.data){
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.loading_icon)
                        error(R.drawable.user_pfp_icon)
                    }
                }
                is NetworkResource.Error -> {
                    Log.d(TAG, "AvatarError: ${it.message}")
                }
                is NetworkResource.Loading -> {
                    Log.d(TAG, "AvatarLoading: ")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}