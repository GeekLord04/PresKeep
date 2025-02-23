package com.geekster.preskeep.ViewModel.HomeViewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekster.preskeep.Repository.HomeRepository.PrescriptionRepositoryImpl
import com.geekster.preskeep.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PrescriptionViewModel @Inject constructor(private val prescriptionRepository: PrescriptionRepositoryImpl) : ViewModel()  {

    private val _prescriptionUploadLiveData = MutableLiveData<NetworkResource<Any>>()
    val prescriptionUploadLiveData: LiveData<NetworkResource<Any>>
        get() = _prescriptionUploadLiveData


    suspend fun uploadPrescription(file : Uri, context : Context){
        _prescriptionUploadLiveData.postValue(NetworkResource.Loading())
        val response = prescriptionRepository.uploadPrescription(file, context)
        _prescriptionUploadLiveData.postValue(response)
    }

}