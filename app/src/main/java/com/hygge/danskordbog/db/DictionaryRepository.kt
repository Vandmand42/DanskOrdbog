package com.hygge.danskordbog.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class DictionaryRepository(private val dictionaryDao: DictionaryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val danishWord: Flow<Dictionary> = dictionaryDao.getVocabulary()

    val allDanishWord: Flow<List<String>> = dictionaryDao.getDanish()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
//    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertVocabulary(danish: Dictionary) {
        dictionaryDao.insertVocabulary(danish)
    }



    @WorkerThread
    suspend fun updateVocabulary(danish: Dictionary) {
        dictionaryDao.updateVocabulary(danish)
    }

    @WorkerThread
    suspend fun deleteVocabulary(danish: Dictionary) {
        dictionaryDao.deleteVocabulary(danish)
    }
}