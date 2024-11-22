package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: ContactRepository) : ViewModel() {
    //stateIn - provides a way to hold a state and emit updates for coroutines.
    val contactsUiState = repository.contacts.stateIn(
        viewModelScope, //viewModelScope - automatically cancels coroutines when the ViewModel is cleared.
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        viewModelScope.launch {
            repository.loadInitialContacts()
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch {
            repository.addRandomContact()
        }
    }
}
