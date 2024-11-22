package at.ac.fhstp.contactsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
    @Insert
    suspend fun addContact(contactEntity: ContactEntity)

    @Update
    suspend fun updateContact(contactEntity: ContactEntity)

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts WHERE _id = :id")
    suspend fun findContactsById(id: Int) : ContactEntity

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE name = :contactName")
    fun findContactsWithName(contactName: String): Flow<List<ContactEntity>>

    // Find contacts by telephone number, sorted by name.
    @Query("SELECT * FROM contacts WHERE telephone_number = :phoneNumber ORDER BY name ASC")
    fun findContactsByPhoneNumberSortedByName(phoneNumber: String): Flow<List<ContactEntity>>

    // Find contacts with age older than 30 years, sorted by name.
    @Query("SELECT * FROM contacts WHERE age > 30 ORDER BY name ASC")
    fun findContactsOlderThan30SortedByName(): Flow<List<ContactEntity>>

    // Find contacts by age and name containing a specific term.
    @Query("SELECT * FROM contacts WHERE age > :age AND name LIKE '%' || :nameTerm || '%' ORDER BY name ASC")
    fun findContactsByAgeAndNameContainingTerm(age: Int, nameTerm: String): Flow<List<ContactEntity>>
}

/*
!! WHEN WE SHOULD ADD suspend? !!
1. One-shot Write Queries - Insert, Update, and Delete operations that modify data in the database.
You need suspend here because it typically has a lot of operations.
With suspend, they can be run on a background thread using coroutines, keeping the main thread free and avoiding potential freezes.
2. One-shot Read Queries - Simple read operations that return data only once, not continuously observed.
You need suspend here because it typically has a lot of operations.
3. Observable Read Queries - Continuous read operations that observe changes in the database and emit updates automatically whenever the data changes.
These functions should not be marked as suspend because Flow and LiveData are already designed to operate asynchronously.
 */