package com.geekster.preskeep.ViewModel.HomeViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekster.preskeep.Repository.HomeRepository.UserRepositoryImpl
import com.geekster.preskeep.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepositoryImpl) : ViewModel()  {

    private val _avatarLiveData = MutableLiveData<NetworkResource<ByteArray>>()
    val avatarLiveData: LiveData<NetworkResource<ByteArray>>
        get() = _avatarLiveData

    private val _userInfoLiveData = MutableLiveData<NetworkResource<Document<Map<String, Any>>>>()
    val userInfoLiveData: LiveData<NetworkResource<Document<Map<String, Any>>>>
        get() = _userInfoLiveData

//    private val _userInfoLiveData = MutableLiveData<NetworkResource<DocumentList<Map<String, Any>>>>()
//    val userInfoLiveData: LiveData<NetworkResource<DocumentList<Map<String, Any>>>>
//        get() = _userInfoLiveData

    suspend fun getAvatar(name : String){
        _avatarLiveData.postValue(NetworkResource.Loading())
        val response = repository.getAvatar(name)
        _avatarLiveData.postValue(response)
    }

    suspend fun getUserInfo(documentId : String){
        _userInfoLiveData.postValue(NetworkResource.Loading())
        val response = repository.getUserInfo(documentId)
        _userInfoLiveData.postValue(response)
    }
}