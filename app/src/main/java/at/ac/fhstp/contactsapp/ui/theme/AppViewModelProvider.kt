package at.ac.fhstp.contactsapp.ui.theme

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.ac.fhstp.contactsapp.ContactsApplication
import at.ac.fhstp.contactsapp.ui.ContactDetailViewModel
import at.ac.fhstp.contactsapp.ui.ContactsViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {

        initializer {
            val contactsApplication = this[APPLICATION_KEY] as ContactsApplication
            ContactsViewModel(contactsApplication.contactRepository)
        }

        initializer {
            val contactsApplication = this[APPLICATION_KEY] as ContactsApplication
            ContactDetailViewModel(
                this.createSavedStateHandle(),
                contactsApplication.contactRepository
            )
        }

    }

}