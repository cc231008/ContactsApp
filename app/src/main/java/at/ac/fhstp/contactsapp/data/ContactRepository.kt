package at.ac.fhstp.contactsapp.data

import at.ac.fhstp.contactsapp.data.db.ContactEntity
import at.ac.fhstp.contactsapp.data.db.ContactsDao
import at.ac.fhstp.contactsapp.data.remote.ContactRemoteService
import kotlinx.coroutines.flow.map

class ContactRepository(
    //it's an object that accesses DAO with its queries (like addContact() or getAllContacts())
    //We will need it here because it can help us to ADD, GET or DELETE something from Database.
    private val contactsDao: ContactsDao,

    private val contactRemoteService: ContactRemoteService
    ) {

    //This line gets all contacts using DAO function.
val contacts = contactsDao.getAllContacts().map { contactList ->
    contactList.map { entity ->
        Contact(entity._id, entity.name, entity.telephoneNumber, entity.age)
    }
}

    suspend fun loadInitialContacts() {
        val remoteContacts = contactRemoteService.getAllContacts()
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
    This function is responsible for adding new contacts.
     We use addContact query from DAO to add a new table (Entity) with contacts.
     */
    suspend fun addRandomContact() {
        contactsDao.addContact(ContactEntity(0, names.random(), 45, "+4357894"))
        }

    /*
    This function is responsible for finding contacts by their id.
    We use findContactById from DAO and get contact's details.
     */
    suspend fun findContactById(contactId: Int): Contact {
        val contactEntity = contactsDao.findContactsById(contactId)
        return  Contact(
            contactEntity._id, contactEntity.name, contactEntity.telephoneNumber, contactEntity.age
        )
    }
    }