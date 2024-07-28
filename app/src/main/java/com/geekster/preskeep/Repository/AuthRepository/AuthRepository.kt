package com.geekster.preskeep.Repository.AuthRepository

import android.content.Context
import com.geekster.preskeep.models.UserRequest
import com.geekster.preskeep.models.otpRequest

interface AuthRepository {

    suspend fun phoneLogin(userRequest: UserRequest)
    suspend fun verifyOtp(otpRequest: otpRequest)
    suspend fun userRegister(userRequest: UserRequest)
    suspend fun checkUser(phoneNumber: String, context : Context) : Boolean
    suspend fun clearLiveData(data : String)

}