package at.ac.fhstp.contactsapp.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = [ContactEntity::class], version = 1)

//ContactsDatabase inherits all methods or properties from RoomDatabase which is a library for Databases.
abstract class ContactsDatabase: RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        /*
        @Volatile is a keyword that helps to access database very quickly
         while other threads are running concurrently.
        */
        @Volatile
        private var Instance: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance to ensure thread safety.
            return Instance ?: synchronized(this) {

                //This creates a new database instance using the Room database builder.
                val instance =
                    Room.databaseBuilder(context, ContactsDatabase::class.java, "contact_database")
                        /**
                         * Setting this option in your app's database builder means that Room
                         * permanently deletes all data from the tables in your database when it
                         * attempts to perform a migration with no defined migration path.
                         * "migration" - refers to the process of updating the database schema to a new version.
                         * That is why we defined version (version = 1)
                         */
                        .fallbackToDestructiveMigration()
                        .build()
                Instance = instance
                return instance
            }
        }
    }
}