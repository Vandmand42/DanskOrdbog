package com.hygge.danskordbog.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVocabulary(dictionary: Dictionary)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVocabulary(dictionary: Dictionary)

    // Remove this since it makes no sense
    @Delete
    suspend fun deleteVocabulary(dictionary: Dictionary)

    @Query("SELECT * FROM dictionary_table WHERE vocab_id == danish")
    fun getVocabulary(): Flow<Dictionary>

    @Query("SELECT DISTINCT danish FROM dictionary_table")
    fun getDanish(): Flow<List<String>>
}