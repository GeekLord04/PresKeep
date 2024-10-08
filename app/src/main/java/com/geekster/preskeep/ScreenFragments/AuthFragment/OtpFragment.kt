package com.geekster.preskeep.ScreenFragments.AuthFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.AuthViewModels.AuthViewModel
import com.geekster.preskeep.databinding.FragmentOtpBinding
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.NetworkResource
import com.geekster.preskeep.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class OtpFragment : Fragment() {

    private var _binding : FragmentOtpBinding? = null

    private val binding get() = _binding!!

    private lateinit var otpRequest : otpRequest

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonVerify.setOnClickListener {
            if (::otpRequest.isInitialized){
                lifecycleScope.launch {
                    authViewModel.verifyOtpWithPhone(otpRequest)
                }
            }
            else{
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.otpView.setOtpCompletionListener {
            otpRequest = otpRequest(it.toString())
        }

        bindObserver()
    }

    private fun bindObserver() {
        authViewModel.userOTPResponseLiveData.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResource.Success -> {
                    Log.d(TAG, "Session ID: ${it.data.toString()}")
                    tokenManager.saveToken("SESSION_ID",it.data!!.id)
                    lifecycleScope.launch {
                        if (authViewModel.checkUserInDatabase(tokenManager.getToken("PHONE_NO")!!, requireContext())){
                            findNavController().navigate(R.id.action_otpFragment_to_homeActivity)
                        }
                        else{
                            findNavController().navigate(R.id.action_otpFragment_to_signupFragment)
                        }
                    }
                }
                is NetworkResource.Error -> {
                    Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
                }
                is NetworkResource.Loading -> {
                    Log.d(TAG, "Otp Fragment Loading")
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}