package at.ac.fhstp.contactsapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Define an interface for making remote service calls to manage contacts
interface ContactRemoteService {

    // Define a GET request to fetch all contacts
    @GET("contacts")
    // The function is a suspend function (for use with coroutines) that returns a list of ContactDto objects
    suspend fun getAllContacts(): List<ContactDto>

    // Define a GET request to fetch a contact by its ID
    @GET("contacts/{contactId}")
    // The function is a suspend function (for use with coroutines) that takes an ID as a path parameter and returns a ContactDto object
    suspend fun getContactsById(@Path("contactId") id: Int): ContactDto

    // Define a POST request to add a new contact
    @POST("contacts")
    // The function is a suspend function (for use with coroutines) that takes a ContactDto object in the request body
    suspend fun addContact(@Body contactDto: ContactDto)
}