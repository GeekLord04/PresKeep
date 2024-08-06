package com.geekster.preskeep.ScreenFragments.AuthFragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.AuthViewModels.AuthViewModel
import com.geekster.preskeep.databinding.FragmentSignupBinding
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.NetworkResource
import com.geekster.preskeep.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        binding.phoneSignup.text = Editable.Factory.getInstance().newEditable(tokenManager.getToken("PHONE_NO"))


//

        binding.buttonSignup.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.userRegister(getUserRequest())
            }
        }

        bindObserver()
    }

    private fun bindObserver() {
        authViewModel.userDatabaseResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResource.Success -> {
                    tokenManager.saveToken("DOCUMENT_ID",it.data!!.id)
                    findNavController().navigate(R.id.action_signupFragment_to_homeActivity)
                }
                is NetworkResource.Error -> {
                    Log.d(TAG, "Signup: ${it.message}")
                }
                is NetworkResource.Loading -> {
                    Log.d(TAG, "Signup: Loading")
                }

            }
        })
    }

    private fun getUserRequest() : UserRequest {
        val name = binding.nameSignup.text.toString()
        val gender = binding.nameGender.text.toString()
        val phoneNo = tokenManager.getToken("PHONE_NO")!!
        return UserRequest(name = name, gender = gender, phoneNo = phoneNo)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}