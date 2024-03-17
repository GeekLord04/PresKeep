package com.geekster.preskeep.ScreenFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.AuthViewModel
import com.geekster.preskeep.databinding.FragmentOtpBinding
import com.geekster.preskeep.databinding.FragmentSignupBinding
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.Resource
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
                is Resource.Success -> {
                    Log.d(TAG, "Session ID: ${it.data.toString()}")
                    tokenManager.saveToken("SESSION_ID",it.data!!.id)
                }
                is Resource.Error -> {
                    Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
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