package com.hygge.danskordbog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Dictionary::class], version = 2, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase(){

    abstract fun dictionaryDao(): DictionaryDao

    private class DictionaryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dictionaryDao())
                }
            }
        }

        suspend fun populateDatabase(dictionaryDao: DictionaryDao) {
            // Delete all content here.
//            dictionaryDao.deleteAll()

            // Add sample words.
            var vocabulary = Dictionary(danish = "hygge", english = "cosiness")
            dictionaryDao.insertVocabulary(vocabulary)
            vocabulary = Dictionary(danish = "hej", english = "hi")
            dictionaryDao.insertVocabulary(vocabulary)
        }
    }
    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: DictionaryDatabase?= null

        fun getDatabase(
            context: Context,
            scope:CoroutineScope
        ): DictionaryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    "dictionary_database"
                ).addCallback(DictionaryDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

