package com.geekster.preskeep.ScreenFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.geekster.preskeep.ViewModel.AuthViewModel
import com.geekster.preskeep.databinding.FragmentSignupBinding
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {


    private var _binding : FragmentSignupBinding? = null

    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (tokenManager.getToken("USER_ID") != null){
            Log.d(TAG, "onViewCreated: ${tokenManager.getToken("USER_ID").toString()}")
        }

        if (tokenManager.getToken("SESSION_ID") != null){
            Log.d(TAG, "onViewCreated: ${tokenManager.getToken("SESSION_ID").toString()}")
        }

//        binding.btnSubmit.setOnClickListener {
//            lifecycleScope.launch{
//                authViewModel.createUserWithPhone(getUserRequest())
//            }
//        }
//
//        binding.btnSubmit2.setOnClickListener {
//            lifecycleScope.launch{
//                authViewModel.verifyOtpWithPhone(getOTPRequest())
//            }
//        }
    }

//    private fun getUserRequest() : UserRequest {
//        val phoneNo = binding.phoneNumber.text.toString()
//        val userId = ID.unique()
//        return UserRequest(userId,phoneNo)
//    }
//
//    private fun getOTPRequest() : otpRequest{
//        val getOtp = binding.phoneNumber2.text.toString()
//        return otpRequest(getOtp)
//    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}