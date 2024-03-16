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
import io.appwrite.Query
import io.appwrite.models.Token
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val account: Account, private val database : Databases) : AuthRepository {

    @Inject
    lateinit var tokenManager: TokenManager


    private val _userResponseLiveData = MutableLiveData<Resource<Token>>()
    val userResponseLiveData: LiveData<Resource<Token>>
        get() = _userResponseLiveData

    private val _userOTPResponseLiveData = MutableLiveData<Resource<String>>()
    val userOTPResponseLiveData: LiveData<Resource<String>>
        get() = _userOTPResponseLiveData


    private val _userDatabaseResponseLiveData = MutableLiveData<Resource<String>>()
    val userDatabaseResponseLiveData: LiveData<Resource<String>>
        get() = _userDatabaseResponseLiveData

    override suspend fun phoneLogin(userRequest : UserRequest){
        try {
            val token = account.createPhoneSession(ID.unique(),userRequest.phoneNo)

//            tokenManager.saveToken("USER_ID",token.userId)

            _userResponseLiveData.postValue(Resource.Success(token))
        }
        catch (e : Exception){
            _userResponseLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun verifyOtp(otpRequest: otpRequest){
        try {
            val session = account.updatePhoneSession(tokenManager.getToken("USER_ID").toString(),otpRequest.otp)
            tokenManager.saveToken("SESSION_ID",session.id)
            Log.d(TAG, "verifyOtp: $session")
            _userOTPResponseLiveData.postValue(Resource.Success(session.toString()))

        }
        catch (e : Exception){
            Log.d(TAG, "verifyOtp: ${e.message}")
            _userOTPResponseLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun userRegister(userRequest: UserRequest) {
        try {
            val getPhoneNumber = database.listDocuments(
                databaseId = "65f1efc89d9549a17e06",
                collectionId = "65f1efd29ed9d3f748ac",
                queries = listOf(Query.equal("Phone",userRequest.phoneNo))
            )
            if (getPhoneNumber.documents.isEmpty()){
                val response = database.createDocument(
                    databaseId = "65f1efc89d9549a17e06",
                    collectionId = "65f1efd29ed9d3f748ac",
                    documentId = ID.unique(),
                    data = mapOf("Name" to userRequest.name, "Gender" to userRequest.gender, "Phone" to userRequest.phoneNo),
                )
                Log.d(TAG, "database response: $response")
//                tokenManager.saveToken("DATABASE_TOKEN",response.id)
                _userDatabaseResponseLiveData.postValue(Resource.Success(response.toString()))
            }
            else{
                Log.d(TAG, "userRegister: Account exists")
            }
        }
        catch (e : Exception){
            Log.d(TAG, "userRegister: ${e.stackTrace}")
        }
    }

    
}

