package com.hygge.danskordbog.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//@Dao
//interface WordDao {
//    @Query("SELECT * FROM word_table ORDER BY word ASC")
//    fun getAlphabetizedWords(): Flow<List<Word>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(word: Word)
//
//    @Query("DELETE FROM word_table")
//    suspend fun deleteAll()
//
//    @Delete
//    suspend fun delete(word: Word)
//}

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(dictionary: Dictionary)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVocabulary(dictionary: Dictionary)

    @Delete
    suspend fun deleteVocabulary(dictionary: Dictionary)

    @Query("SELECT * FROM dictionary_table WHERE vocab_id == danish")
    fun getVocabulary(): Flow<Dictionary>

    @Query("SELECT DISTINCT danish FROM dictionary_table")
    fun getDanish(): Flow<List<String>>
}