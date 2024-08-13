package com.geekster.preskeep.utils

import com.geekster.preskeep.BuildConfig


object Constants {

    const val TAG = "PRESKEEP_TAG"
    const val BASE_URL = "https://cloud.appwrite.io/v1"

//    const val PROJECT_ID = "65a2cbc06a988efed38b"
//    const val PREFS_TOKEN_FILE = "PREFS_TOKEN_FILE"
//    const val DATABASE_ID = "65f1efc89d9549a17e06"
//    const val COLLECTION_ID = "65f1efd29ed9d3f748ac"

    const val PROJECT_ID = BuildConfig.PROJECT_ID
    const val PREFS_TOKEN_FILE = BuildConfig.PREFS_TOKEN_FILE
    const val DATABASE_ID = BuildConfig.DATABASE_ID
    const val COLLECTION_ID = BuildConfig.COLLECTION_ID
}