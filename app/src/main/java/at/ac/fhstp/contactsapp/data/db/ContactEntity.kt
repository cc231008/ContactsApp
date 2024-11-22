package at.ac.fhstp.contactsapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity - is a class that represents a table in your database.
@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val age: Int,
    @ColumnInfo("telephone_number")
    val telephoneNumber: String
)
