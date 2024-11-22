package at.ac.fhstp.contactsapp

import android.app.Application
import at.ac.fhstp.contactsapp.data.ContactRepository
import at.ac.fhstp.contactsapp.data.db.ContactsDatabase
import at.ac.fhstp.contactsapp.data.remote.ContactRemoteService
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ContactsApplication : Application() {

    val contactRepository by lazy {
        val contactsDao = ContactsDatabase.getDatabase(this).contactsDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/GithubGenericUsername/find-your-pet/")
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(MediaType.get("application/json")))
            .build()

        val contactRemoteService = retrofit.create(ContactRemoteService::class.java)

        ContactRepository(contactsDao, contactRemoteService)
    }

}
