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
    private val savedStateHandle: SavedStateHandle,
    private  val repository: ContactRepository) : ViewModel() {

    private val contactId : Int = savedStateHandle["contactId"] ?: 0

    private  val _contactDetailUiState = MutableStateFlow(ContactDetailUiState(
        Contact(0, "", "", 0)
    ))
    val contactDetailUiState = _contactDetailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val contact = repository.findContactById(contactId)
            _contactDetailUiState.update {
                it.copy(contact = contact)
            }
        }
    }
}