package com.example.estudotrabalhofinal.pages.main


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estudotrabalhofinal.data.DietWithFoods
import com.example.estudotrabalhofinal.pages.main.*

@Composable
fun ProgressScreen(viewModel: ProgressViewModel = viewModel()) {
    val diets by viewModel.diets.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Meus Planos de Dieta",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        if (diets.isEmpty()) {
            item {
                Text(
                    "Nenhuma dieta cadastrada ainda. Vá para a aba 'Dietas' para criar uma!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            items(diets, key = { it.diet.id }) { dietWithFoods ->
                DietCard(
                    dietWithFoods = dietWithFoods,
                    onDeleteDiet = { viewModel.deleteDiet(dietWithFoods.diet) },
                    onRemoveFood = { food -> viewModel.removeFoodFromDiet(food, dietWithFoods.diet) }
                )
            }
        }
    }
}

@Composable
fun DietCard(
    dietWithFoods: DietWithFoods,
    onDeleteDiet: () -> Unit,
    onRemoveFood: (com.example.estudotrabalhofinal.data.Food) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabeçalho do Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(dietWithFoods.diet.name, style = MaterialTheme.typography.titleLarge)
                    Text("Horário: ${dietWithFoods.diet.time}", style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onClick = onDeleteDiet) {
                    Icon(Icons.Default.Delete, contentDescription = "Deletar dieta")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Lista de alimentos
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                dietWithFoods.foods.forEach { food ->
                    FoodItemRow(food = food) {
                        onRemoveFood(food)
                    }
                }
            }

            // Totalizador de calorias
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Total: ${dietWithFoods.totalCalories} kcal",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FoodItemRow(
    food: com.example.estudotrabalhofinal.data.Food,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "• ${food.name} (${food.calories} kcal)",
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )
        IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Remover alimento", modifier = Modifier.size(18.dp))
        }
    }
}