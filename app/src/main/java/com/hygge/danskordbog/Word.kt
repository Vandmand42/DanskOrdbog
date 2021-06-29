package com.hygge.danskordbog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

//@ColumnInfo(name = "word") val word: String,
//@PrimaryKey(autoGenerate = true) val id: Int
