package com.example.estudotrabalhofinal.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estudotrabalhofinal.data.Food
import com.example.estudotrabalhofinal.pages.main.*

@Composable
fun FoodAnalyzerScreen(
    // Passo 1: Instanciar o ViewModel. Isto irá forçar a criação/abertura do banco de dados.
    viewModel: FoodAnalyzerViewModel = viewModel()
) {
    // Passo 2: Coletar os estados diretamente do ViewModel.
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val selectedFoods by viewModel.selectedFoods.collectAsState()
    val dietName by viewModel.dietName.collectAsState()
    val dietTime by viewModel.dietTime.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // SEÇÃO DE BUSCA
        item {
            Text("Analisador de Alimentos", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = searchQuery,
                // Passo 3: Conectar as ações do usuário às funções do ViewModel.
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite o nome do alimento...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ícone de busca") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpar busca")
                        }
                    }
                }
            )
        }

        // RESULTADOS DA BUSCA (Vindos do Banco de Dados)
        if (searchResults.isNotEmpty()) {
            item {
                Text("Resultados da Busca", style = MaterialTheme.typography.titleMedium)
            }
            items(searchResults) { food ->
                FoodSearchResultItem(food = food) {
                    viewModel.addFoodToSelection(food)
                }
            }
        }

        // ALIMENTOS SELECIONADOS
        if (selectedFoods.isNotEmpty()) {
            item {
                Text("Alimentos Selecionados para a Dieta", style = MaterialTheme.typography.titleMedium)
            }
            items(selectedFoods) { food ->
                SelectedFoodItem(food = food) {
                    viewModel.removeFoodFromSelection(food)
                }
            }
        }

        // SEÇÃO PARA CRIAR DIETA
        if (selectedFoods.isNotEmpty()) {
            item {
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text("Criar Nova Dieta", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = dietName,
                    onValueChange = { viewModel.onDietNameChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nome da Dieta") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = dietTime,
                    onValueChange = { viewModel.onDietTimeChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Horário da Refeição (ex: 08:00)") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Passo 4: Chamar a função correta para salvar a dieta no banco.
                        viewModel.createDiet()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = dietName.isNotBlank() && dietTime.isNotBlank()
                ) {
                    Text("CRIAR DIETA")
                }
            }
        }
    }
}

// Os Composables de item abaixo não precisam de alteração.

@Composable
fun FoodSearchResultItem(food: Food, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(food.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${food.calories} kcal", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar ${food.name} à dieta")
            }
        }
    }
}

@Composable
fun SelectedFoodItem(food: Food, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "• ${food.name} (${food.calories} kcal)", modifier = Modifier.weight(1f))
        IconButton(onClick = onRemoveClick) {
            Icon(Icons.Default.Clear, contentDescription = "Remover ${food.name} da seleção")
        }
    }
}