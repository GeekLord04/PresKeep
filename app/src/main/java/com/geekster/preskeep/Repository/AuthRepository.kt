package com.geekster.preskeep.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.models.otpRequest
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.Resource
import com.geekster.preskeep.utils.TokenManager
import io.appwrite.ID
import io.appwrite.services.Account
import javax.inject.Inject


class AuthRepository @Inject constructor(private val account: Account) {

    lateinit var finalToken : String

    @Inject
    lateinit var tokenManager: TokenManager


    private val _userResponseLiveData = MutableLiveData<Resource<String>>()
    val userResponseLiveData: LiveData<Resource<String>>
        get() = _userResponseLiveData

    private val _userOTPResponseLiveData = MutableLiveData<Resource<String>>()
    val userOTPResponseLiveData: LiveData<Resource<String>>
        get() = _userOTPResponseLiveData


    suspend fun phoneLogin(userRequest: UserRequest){
        try {
            val token = account.createPhoneSession(ID.unique(),userRequest.phoneNo)

            tokenManager.saveToken("USER_ID",token.userId)
//            userResponse.token = token

//            Log.d(TAG, "phoneLogin: ${token.userId}")
            _userResponseLiveData.postValue(Resource.Success(token.userId))
        }
        catch (e : Exception){
            _userResponseLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }

    suspend fun verifyOtp(otpRequest: otpRequest){
        try {
            val session = account.updatePhoneSession(tokenManager.getToken("USER_ID").toString(),otpRequest.otp)
            tokenManager.saveToken("SESSION_ID",session.id)
            Log.d(TAG, "verifyOtp: $session")
            _userResponseLiveData.postValue(Resource.Success(session.toString()))

        }
        catch (e : Exception){
//            Log.d(TAG, "verifyOtp: ${e.message}")
            _userResponseLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }
    
}

