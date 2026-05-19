package com.example.notey.roomdb

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName =  "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int  = 0,
    val title : String,
    val description: String,
    val color : Int,
)
