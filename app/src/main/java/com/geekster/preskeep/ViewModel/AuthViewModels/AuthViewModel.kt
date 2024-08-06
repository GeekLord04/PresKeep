package com.geekster.preskeep.ViewModel.AuthViewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekster.preskeep.Repository.AuthRepository.AuthRepositoryImpl
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.models.Document
import io.appwrite.models.Session
import io.appwrite.models.Token
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepositoryImpl) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResource<Token>>
        get() = repository.userResponseLiveData

    val userOTPResponseLiveData: LiveData<NetworkResource<Session>>
        get() = repository.userOTPResponseLiveData

    val userDatabaseResponseLiveData : LiveData<NetworkResource<Document<Map<String, Any>>>>
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

    suspend fun checkUserInDatabase(phoneNumber : String, context : Context) : Boolean{
        val deferredJob = viewModelScope.async {
            repository.checkUser(phoneNumber, context)
        }
        return deferredJob.await()
    }

    suspend fun clearLiveData(data : String){
        repository.clearLiveData(data)
    }


}
