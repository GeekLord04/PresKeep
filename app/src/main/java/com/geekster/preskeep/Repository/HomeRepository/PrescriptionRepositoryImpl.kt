package com.geekster.preskeep.Repository.HomeRepository


import android.net.Uri
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
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import android.content.Context
import com.geekster.preskeep.utils.Constants.PRESCRIPTION_STORAGE_ID
import io.appwrite.ID
import io.appwrite.models.InputFile
import io.appwrite.services.Storage

class PrescriptionRepositoryImpl @Inject constructor(private val database : Databases, private val storage : Storage) : PrescriptionRepository {

    @Inject lateinit var tokenManager: TokenManager

    override suspend fun uploadPrescription(file: Uri, context: Context): NetworkResource<Any> {

        val inputStream = context.contentResolver.openInputStream(file)
        val prescriptionFile = createFileFromInputStream(inputStream)

        try {
            val uploadFileToStorage = storage.createFile(                    //Upload into the storage
                bucketId = PRESCRIPTION_STORAGE_ID,
                fileId = ID.unique(),
                file = InputFile.fromFile(prescriptionFile),
            )

            // Fetch the existing document
            val document = database.getDocument(
                databaseId = DATABASE_ID,
                collectionId = COLLECTION_ID,
                documentId = tokenManager.getToken("DOCUMENT_ID").toString()
            )

            // Get the existing list or initialize an empty one if it doesn't exist
            val existingPrescriptions = document.data["Prescriptions"] as? List<String> ?: emptyList()

            // Append the new ID
            val updatedPrescriptions = existingPrescriptions + uploadFileToStorage.id

            val result = database.updateDocument(
                databaseId = DATABASE_ID,
                collectionId = COLLECTION_ID,
                documentId = tokenManager.getToken("DOCUMENT_ID").toString(),
                data = mapOf(
                    "Prescriptions" to updatedPrescriptions
                )
            )

            return NetworkResource.Success(result)              //If we need the chunks uploaded, resturn the uploadFile
        }
        catch (e : Exception){
            return NetworkResource.Error(e.message.toString())
        }
    }




    private fun createFileFromInputStream(inputStream: InputStream?): File {
        val outputFile = File.createTempFile("Prescription", "")
        inputStream?.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return outputFile
    }
}