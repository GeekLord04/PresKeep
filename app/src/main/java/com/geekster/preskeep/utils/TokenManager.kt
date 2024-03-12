package com.geekster.preskeep.utils

import android.content.Context
import com.geekster.preskeep.utils.Constants.PREFS_TOKEN_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(key : String,token : String){
        val editor = prefs.edit()
        editor.putString(key, token)
        editor.apply()
    }

    fun getToken(key:String) : String? {
        return prefs.getString(key,null)
    }
}