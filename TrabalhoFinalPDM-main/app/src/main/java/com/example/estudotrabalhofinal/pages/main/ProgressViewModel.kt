package com.example.estudotrabalhofinal.pages.main
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.estudotrabalhofinal.data.AppDatabase
import com.example.estudotrabalhofinal.data.Diet
import com.example.estudotrabalhofinal.data.Food
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val dietDao = AppDatabase.getInstance(application).dietDao()

    // Carrega todas as dietas e as expõe como um StateFlow
    val diets = dietDao.getDietsWithFoods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun deleteDiet(diet: Diet) {
        viewModelScope.launch {
            dietDao.deleteDiet(diet)
        }
    }

    fun removeFoodFromDiet(food: Food, diet: Diet) {
        viewModelScope.launch {
            dietDao.deleteFoodFromDiet(dietId = diet.id, foodId = food.id)
        }
    }
}