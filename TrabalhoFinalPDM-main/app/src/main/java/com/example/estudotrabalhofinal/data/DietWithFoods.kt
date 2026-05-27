package com.example.estudotrabalhofinal.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Representa a relação completa de uma Dieta com sua lista de Alimentos.
 */
data class DietWithFoods(
    @Embedded val diet: Diet,
    @Relation(
        parentColumn = "id", // Chave primária da tabela 'diets'
        entityColumn = "id", // Chave primária da tabela 'foods'
        associateBy = Junction(
            value = DietFoodCrossRef::class,
            parentColumn = "dietId", // Coluna em DietFoodCrossRef que aponta para Diet
            entityColumn = "foodId"  // Coluna em DietFoodCrossRef que aponta para Food
        )
    )
    val foods: List<Food>
) {
    // Propriedade calculada para obter o total de calorias
    val totalCalories: Int
        get() = foods.sumOf { it.calories }
}