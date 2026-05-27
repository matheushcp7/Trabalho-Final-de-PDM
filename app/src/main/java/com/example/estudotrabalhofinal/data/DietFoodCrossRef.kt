package com.example.estudotrabalhofinal.data

import androidx.room.Entity

@Entity(primaryKeys = ["dietId", "foodId"])
data class DietFoodCrossRef(
    val dietId: Int,
    val foodId: Int
)