package com.geekster.preskeep.Repository.HomeRepository

import com.geekster.preskeep.utils.NetworkResource
import io.appwrite.models.Document
import io.appwrite.models.DocumentList

interface UserRepository {

    suspend fun getAvatar(name : String) : NetworkResource<ByteArray>

    suspend fun getUserInfo(documentId : String) : NetworkResource<Document<Map<String, Any>>>
}