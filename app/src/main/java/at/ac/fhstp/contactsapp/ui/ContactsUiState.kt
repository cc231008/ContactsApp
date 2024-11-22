package at.ac.fhstp.contactsapp.ui

import at.ac.fhstp.contactsapp.data.Contact

data class ContactsUiState(
    val contacts : List<Contact>,
    val selectedCardIndex: Int?
)
