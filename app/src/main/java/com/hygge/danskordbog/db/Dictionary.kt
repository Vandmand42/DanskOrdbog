package com.hygge.danskordbog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_table", indices = [Index(value = ["danish", "english"], unique = true)])
data class Dictionary(

    val danish: String,
    val english: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vocab_id")
    val id: Int = 0
    )