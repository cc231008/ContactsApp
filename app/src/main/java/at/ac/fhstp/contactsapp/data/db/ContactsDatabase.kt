package at.ac.fhstp.contactsapp.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile
        private var Instance: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, ContactsDatabase::class.java, "contact_database")
                        /**
                         * Setting this option in your app's database builder means that Room
                         * permanently deletes all data from the tables in your database when it
                         * attempts to perform a migration with no defined migration path.
                         */
                        .fallbackToDestructiveMigration()
                        .build()
                Instance = instance
                return instance
            }
        }
    }
}