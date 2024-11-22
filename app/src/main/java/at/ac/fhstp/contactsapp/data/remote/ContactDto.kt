package at.ac.fhstp.contactsapp.data.remote

import kotlinx.serialization.Serializable

//Data Transfer Object used for transferring contact data between the remote service and the application.
@Serializable
data class ContactDto(
    val id: Int,
    val name: String,
    val telephoneNumber: Int,
    val age: Int
)
