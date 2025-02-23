package com.geekster.preskeep.Repository.HomeRepository

import android.content.Context
import android.net.Uri
import com.geekster.preskeep.utils.NetworkResource
import io.appwrite.models.Document
import io.appwrite.models.DocumentList

interface PrescriptionRepository {

//    suspend fun getAvatar(name : String) : NetworkResource<ByteArray>
//
//    suspend fun getUserInfo(documentId : String) : NetworkResource<Document<Map<String, Any>>>
//
//    suspend fun logoutUser(session : String) : NetworkResource<Any>



    suspend fun uploadPrescription(file : Uri, context: Context) : NetworkResource<Any>
}