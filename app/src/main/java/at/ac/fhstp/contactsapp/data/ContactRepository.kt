package at.ac.fhstp.contactsapp.data

import at.ac.fhstp.contactsapp.data.db.ContactEntity
import at.ac.fhstp.contactsapp.data.db.ContactsDao
import at.ac.fhstp.contactsapp.data.remote.ContactRemoteService
import kotlinx.coroutines.flow.map

/*
 ContactRepository is responsible for managing data operations related to contacts.
 It acts as an intermediary between the data sources (like a local database and remote API) and the rest of the application.
 */
class ContactRepository(
    //ContactRepository wants to interact and manipulate data from the database.
    private val contactsDao: ContactsDao,
    //ContactRepository wants to interact and manipulate data from the remote API.
    private val contactRemoteService: ContactRemoteService
    ) {

    //This line fetches all contacts from the database and maps them to a list of Contact objects.
    val contacts = contactsDao.getAllContacts()
        .map { contactList ->
            contactList.map { entity ->
                Contact(entity._id, entity.name, entity.telephoneNumber, entity.age)
            }
        }

    //This line loads contacts from the remote API and adds them to the local database.
    suspend fun loadInitialContacts() {
        val remoteContacts = contactRemoteService.getAllContacts()
        //dto (ContactDto) fetches data from the remote service (API) and map it to local entities.
        remoteContacts.map {
            dto -> ContactEntity(0, dto.name, dto.age, dto.telephoneNumber.toString())
        }.forEach {
            entity -> contactsDao.addContact(entity)
        }
    }

//List of names
    val names = listOf(
        "Max",
        "Tom",
        "Anna",
        "Matt"
    )

    /*
    This function is responsible for adding new contacts to the database.
     We use addContact query from DAO to add a new table (Entity) with contacts.
     */
    suspend fun addRandomContact() {
        contactsDao.addContact(ContactEntity(0, names.random(), 45, "+4357894"))
        }

    /*
    This function is responsible for finding contacts by their id to the database.
    We use findContactById from DAO and get contact's details.
     */
    suspend fun findContactById(contactId: Int): Contact {
        val contactEntity = contactsDao.findContactsById(contactId)
        return Contact(
            contactEntity._id, contactEntity.name, contactEntity.telephoneNumber, contactEntity.age
        )
    }
    }