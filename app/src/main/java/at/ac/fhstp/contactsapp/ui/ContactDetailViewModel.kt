package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.contactsapp.data.Contact
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactDetailUiState(
    val contact: Contact
)

class ContactDetailViewModel(
    private val savedStateHandle: SavedStateHandle, //Provides a way to retrieve and save state information.
    private  val repository: ContactRepository) : ViewModel() {

    //Retrieves the contact ID from the saved state
    private val contactId : Int = savedStateHandle["contactId"] ?: 0

    //MutableStateFlow to hold the contact detail UI state
    private  val _contactDetailUiState = MutableStateFlow(ContactDetailUiState(
        Contact(0, "", "", 0) //Default empty contact
    ))
    //Publicly exposed StateFlow for observing the UI state
    val contactDetailUiState = _contactDetailUiState.asStateFlow()

    //Initialization block to load contact details
    init {
        viewModelScope.launch {
            val contact = repository.findContactById(contactId) //Fetches contact by ID from the repository
            _contactDetailUiState.update {
                it.copy(contact = contact) //Updates the UI state with the fetched contact
            }
        }
    }
}