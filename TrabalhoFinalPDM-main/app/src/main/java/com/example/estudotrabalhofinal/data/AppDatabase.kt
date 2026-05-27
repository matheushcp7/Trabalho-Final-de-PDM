package com.example.estudotrabalhofinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Food::class, Diet::class, DietFoodCrossRef::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun dietDao(): DietDao

    companion object {
        // A anotação @Volatile garante que o valor de INSTANCE seja sempre atualizado
        // e visível para todas as threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // O bloco synchronized garante que apenas uma thread possa executar este código
            // por vez, evitando a criação de múltiplas instâncias do banco de dados.
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        // A MÁGICA ACONTECE AQUI: O Callback é adicionado diretamente
                        .addCallback(DatabaseCallback(context))
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    /**
     * O Callback agora é uma classe privada dentro do AppDatabase.
     * Isso mantém tudo encapsulado e organizado.
     */
    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Lançamos uma coroutine para popular os dados em background.
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.foodDao())
                }
            }
        }

        suspend fun populateDatabase(foodDao: FoodDao) {
            // Pega a lista de alimentos do nosso arquivo InitialData
            val initialFoods = InitialData.getInitialFoods()
            // Insere a lista no banco de dados
            foodDao.insertAll(initialFoods)
        }
    }
}