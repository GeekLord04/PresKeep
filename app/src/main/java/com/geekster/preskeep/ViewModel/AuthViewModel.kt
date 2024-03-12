package com.geekster.preskeep.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekster.preskeep.Repository.AuthRepository
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    val userResponseLiveData: LiveData<Resource<String>>
        get() = repository.userResponseLiveData

    val userOTPResponseLiveData: LiveData<Resource<String>>
        get() = repository.userOTPResponseLiveData


    fun createUserWithPhone(userRequest : UserRequest)  {
        viewModelScope.launch {
            repository.phoneLogin(userRequest)
        }
    }

    fun verifyOtpWithPhone(otpRequest: otpRequest){
        viewModelScope.launch {
            repository.verifyOtp(otpRequest)
        }
    }

}
