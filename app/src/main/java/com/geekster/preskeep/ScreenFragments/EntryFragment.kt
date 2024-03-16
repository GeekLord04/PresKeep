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
import androidx.navigation.fragment.findNavController
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.AuthViewModel
import com.geekster.preskeep.databinding.FragmentEntryBinding
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.Resource
import com.geekster.preskeep.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.ID
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EntryFragment : Fragment() {

    private var _binding : FragmentEntryBinding ?= null

    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager : TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =  FragmentEntryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tokenManager.getToken("SESSION_ID") != null){
            Log.d(TAG, "Session ID: ${tokenManager.getToken("SESSION_ID").toString()}")
        }
        if (tokenManager.getToken("USER_ID") != null){
            Log.d(TAG, "USER ID: ${tokenManager.getToken("USER_ID").toString()}")
        }

        binding.buttonVerify.setOnClickListener {
            if (binding.phoneNumber.text.toString().isNotEmpty()){
                lifecycleScope.launch {
                    authViewModel.createUserWithPhone(getUserRequest())
                }
            }
            else{
                Toast.makeText(context,"Please enter your Phone number",Toast.LENGTH_SHORT).show()
            }
        }
        bindObserver()

    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    tokenManager.saveToken("USER_ID", it.data!!.id)
                    findNavController().navigate(R.id.action_entryFragment_to_otpFragment)
                }

                is Resource.Error -> {
                    Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }
            }

        })
    }

    private fun getUserRequest() : UserRequest {
        val phoneNo = binding.phoneNumber.text.toString()
        val userId = ID.unique()
        return UserRequest(uniqueId = userId, phoneNo = phoneNo)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}