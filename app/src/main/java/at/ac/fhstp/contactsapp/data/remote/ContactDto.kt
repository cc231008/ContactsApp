package at.ac.fhstp.contactsapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    val id: Int,
    val name: String,
    val telephoneNumber: Int,
    val age: Int
)
