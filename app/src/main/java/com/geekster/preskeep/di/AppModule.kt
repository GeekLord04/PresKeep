package com.geekster.preskeep.di

import android.content.Context
import com.geekster.preskeep.utils.Constants.BASE_URL
import com.geekster.preskeep.utils.Constants.PROJECT_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import javax.inject.Singleton

@InstallIn(SingletonComponent :: class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun createClient(@ApplicationContext context: Context): Client {
        return Client(context)
            .setEndpoint(BASE_URL)
            .setProject(PROJECT_ID)
            .setSelfSigned(status = true)
    }

    @Provides
    @Singleton
    fun providesAppWriteAccount(appWrite: Client): Account {
        return Account(appWrite)
    }

    @Provides
    @Singleton
    fun providesAppWriteDatabase(appWrite: Client) : Databases{
        return Databases(appWrite)
    }

    @Provides
    @Singleton
    fun providesAppWriteStorage(appWrite: Client) : Storage{
        return Storage(appWrite)
    }

}