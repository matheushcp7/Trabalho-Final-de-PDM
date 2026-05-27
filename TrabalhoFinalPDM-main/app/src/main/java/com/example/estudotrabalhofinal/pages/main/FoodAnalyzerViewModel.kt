package com.example.estudotrabalhofinal.pages.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.estudotrabalhofinal.data.AppDatabase
import com.example.estudotrabalhofinal.data.Diet
import com.example.estudotrabalhofinal.data.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodAnalyzerViewModel(application: Application) : AndroidViewModel(application) {

    private val foodDao = AppDatabase.getInstance(application).foodDao()
    private val dietDao = AppDatabase.getInstance(application).dietDao()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Food>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _selectedFoods = MutableStateFlow<List<Food>>(emptyList())
    val selectedFoods = _selectedFoods.asStateFlow()

    // --- NOVOS ESTADOS ADICIONADOS ---
    private val _dietName = MutableStateFlow("")
    val dietName = _dietName.asStateFlow()

    private val _dietTime = MutableStateFlow("")
    val dietTime = _dietTime.asStateFlow()

    // --- NOVAS FUNÇÕES ADICIONADAS ---
    fun onDietNameChange(name: String) {
        _dietName.value = name
    }

    fun onDietTimeChange(time: String) {
        _dietTime.value = time
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.length > 1) {
            viewModelScope.launch {
                _searchResults.value = foodDao.searchFoodsByName(query)
            }
        } else {
            _searchResults.value = emptyList()
        }
    }

    fun addFoodToSelection(food: Food) {
        if (!_selectedFoods.value.contains(food)) {
            _selectedFoods.update { currentList -> currentList + food }
        }
    }

    fun removeFoodFromSelection(food: Food) {
        _selectedFoods.update { currentList -> currentList - food }
    }

    // --- FUNÇÃO createDiet MODIFICADA ---
    // Não precisa mais de parâmetros, pois já possui o estado.
    fun createDiet() {
        val currentDietName = _dietName.value
        val currentDietTime = _dietTime.value

        if (currentDietName.isBlank() || currentDietTime.isBlank() || _selectedFoods.value.isEmpty()) {
            // A validação ainda existe, mas agora usa o estado do próprio ViewModel.
            return
        }

        viewModelScope.launch {
            val newDiet = Diet(name = currentDietName, time = currentDietTime)
            dietDao.createDietWithFoods(newDiet, _selectedFoods.value)
            // Limpa o estado após a criação da dieta
            clearDietCreationState()
        }
    }

    private fun clearDietCreationState() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        _selectedFoods.value = emptyList()
        _dietName.value = "" // Limpa o nome da dieta
        _dietTime.value = "" // Limpa o horário da dieta
    }
}