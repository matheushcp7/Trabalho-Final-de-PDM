package com.example.estudotrabalhofinal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {

    /**
     * Insere uma nova dieta na tabela de dietas.
     * @return O ID da linha recém-inserida.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiet(diet: Diet): Long

    /**
     * Insere uma referência cruzada entre uma dieta e um alimento.
     * Usado para associar um alimento a uma dieta específica.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDietFoodCrossRef(crossRef: DietFoodCrossRef)

    /**
     * Método transacional para criar uma dieta completa com seus alimentos.
     * Primeiro, insere a dieta, obtém seu novo ID e, em seguida,
     * associa cada alimento à dieta usando a tabela de referência cruzada.
     */
    @Transaction
    suspend fun createDietWithFoods(diet: Diet, foods: List<Food>) {
        // Insere a dieta e obtém o ID gerado automaticamente
        val dietId = insertDiet(diet)

        // Para cada alimento na lista, cria a relação com a nova dieta
        foods.forEach { food ->
            insertDietFoodCrossRef(
                DietFoodCrossRef(
                    dietId = dietId.toInt(),
                    foodId = food.id
                )
            )
        }
    }
    @Transaction
    @Query("SELECT * FROM diets ORDER BY time ASC")
    fun getDietsWithFoods(): Flow<List<DietWithFoods>>

    /**
     * Deleta uma dieta inteira. O Room cuidará de remover as referências
     * se houver um foreign key com `onDelete = CASCADE`.
     * Ou deletamos manualmente as referências.
     */
    @Delete
    suspend fun deleteDiet(diet: Diet)

    /**
     * Remove um alimento específico de uma dieta.
     */
    @Query("DELETE FROM DietFoodCrossRef WHERE dietId = :dietId AND foodId = :foodId")
    suspend fun deleteFoodFromDiet(dietId: Int, foodId: Int)
}