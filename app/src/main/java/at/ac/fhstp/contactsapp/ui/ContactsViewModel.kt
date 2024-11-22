package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ContactsViewModel(private val repository: ContactRepository) : ViewModel() {
    //contactsUiState is initialized to hold the state of contacts.
    //stateIn - provides a way to hold a state and emit updates.
    val contactsUiState = repository.contacts.stateIn(
        viewModelScope, //viewModelScope - automatically cancels coroutines when the ViewModel is cleared.
        SharingStarted.WhileSubscribed(5000), //keeps the state active while there are subscribers, with a delay of 5000 ms before stopping.
        emptyList() //The initial state.
    )

    //An init block launches a coroutine in the viewModelScope to load initial contacts from the repository.
    init {
        viewModelScope.launch {
            repository.loadInitialContacts()
        }
    }

//Defines a function onAddButtonClicked that launches a coroutine to add a random contact
    fun onAddButtonClicked() {
        viewModelScope.launch {
            repository.addRandomContact()
        }
    }
}
