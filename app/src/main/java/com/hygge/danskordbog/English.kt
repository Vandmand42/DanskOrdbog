package com.hygge.danskordbog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "english_table")
data class English(
    @ColumnInfo(name = "danish") val word: String,
    @PrimaryKey(autoGenerate = true) val id: Int
)