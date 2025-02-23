package com.geekster.preskeep.Repository.HomeRepository


import android.util.Log
import com.geekster.preskeep.utils.Constants.COLLECTION_ID
import com.geekster.preskeep.utils.Constants.DATABASE_ID
import com.geekster.preskeep.utils.Constants.TAG
import com.geekster.preskeep.utils.NetworkResource
import com.geekster.preskeep.utils.TokenManager
import io.appwrite.Query
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Account
import io.appwrite.services.Avatars
import io.appwrite.services.Databases
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val avatars: Avatars, private val database : Databases, private val account: Account) : UserRepository {

    override suspend fun getAvatar(name: String): NetworkResource<ByteArray> {
        try {
            val avatarResponse = avatars.getInitials(name, background = "001B48")
            return NetworkResource.Success(avatarResponse)

        }
        catch (e : Exception){
            return NetworkResource.Error(e.message.toString())
        }
    }

    override suspend fun getUserInfo(documentId : String): NetworkResource<Document<Map<String, Any>>> {
        try {
            val result = database.getDocument(
                DATABASE_ID,
                COLLECTION_ID,
                documentId
            )
            Log.d(TAG, "Document: $result")
            return NetworkResource.Success(result)
        }
        catch (e : Exception){
            return NetworkResource.Error(e.message.toString())

        }
    }

    override suspend fun logoutUser(session : String) : NetworkResource<Any> {
        try{
            val result = account.deleteSession(session)
            return NetworkResource.Success(result)
        }
        catch (e : Exception){
            return NetworkResource.Error(e.message.toString())
        }
    }
//    override suspend fun getUserInfo(documentId : String): NetworkResource<DocumentList<Map<String, Any>>> {
//        try {
//            val result = database.listDocuments(
//                DATABASE_ID,
//                COLLECTION_ID,
//                queries = listOf(Query.equal("Phone", "+916000570140"))
//            )
//            Log.d(TAG, "UserRepo: $result ")
//            return NetworkResource.Success(result)
//        }
//        catch (e : Exception){
//            return NetworkResource.Error(e.message.toString())
//
//        }
//    }


}