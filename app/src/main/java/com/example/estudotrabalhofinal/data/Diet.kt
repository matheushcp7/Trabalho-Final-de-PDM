package com.example.estudotrabalhofinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diets")
data class Diet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String // Formato "HH:mm", ex: "08:30"
)