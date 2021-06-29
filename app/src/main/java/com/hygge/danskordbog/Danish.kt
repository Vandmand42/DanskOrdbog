package com.hygge.danskordbog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "danish_table")
data class Danish(
    @ColumnInfo(name = "danish") val word: String,
    @PrimaryKey(autoGenerate = true) val id: Int
    )