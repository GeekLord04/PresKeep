package com.geekster.preskeep.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekster.preskeep.Repository.AuthRepositoryImpl
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.models.Token
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepositoryImpl) : ViewModel() {

    val userResponseLiveData: LiveData<Resource<Token>>
        get() = repository.userResponseLiveData

    val userOTPResponseLiveData: LiveData<Resource<String>>
        get() = repository.userOTPResponseLiveData

    val userDatabaseResponseLiveData : LiveData<Resource<String>>
        get() = repository.userDatabaseResponseLiveData

    suspend fun createUserWithPhone(userRequest : UserRequest)  {
        viewModelScope.launch {
            repository.phoneLogin(userRequest)
        }
    }

    suspend fun verifyOtpWithPhone(otpRequest: otpRequest){
        viewModelScope.launch {
            repository.verifyOtp(otpRequest)
        }
    }

    suspend fun userRegister(userRequest: UserRequest){
        viewModelScope.launch {
            repository.userRegister(userRequest)
        }
    }

}
