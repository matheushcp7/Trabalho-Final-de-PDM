package com.example.estudotrabalhofinal.data

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class PrepopulateCallback(
    // Usamos Provider para evitar um problema de dependência circular com Hilt
    private val foodDaoProvider: Provider<FoodDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // Inicia uma coroutine para inserir os dados em background
        CoroutineScope(Dispatchers.IO).launch {
            val foodDao = foodDaoProvider.get()
            // Insere todos os alimentos da nossa lista inicial
            foodDao.insertAll(InitialData.getInitialFoods())
        }
    }
}