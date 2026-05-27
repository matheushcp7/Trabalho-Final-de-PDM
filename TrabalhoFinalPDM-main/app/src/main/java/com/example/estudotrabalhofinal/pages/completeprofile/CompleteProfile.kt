package com.example.estudotrabalhofinal.pages.completeprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.estudotrabalhofinal.R
import com.example.estudotrabalhofinal.navgation.Screen // Correção do import de navegação
import com.example.estudotrabalhofinal.pages.theme.*

// ViewModel para a tela
class CompleteProfileViewModel(navController: NavHostController) : ViewModel() {
    val dateOfBirth = mutableStateOf("")
    val gender = mutableStateOf("")
    val weight = mutableStateOf("")
    val dietaryPreferences = listOf("Vegetarian", "Vegan", "Gluten-Free", "Nut-Free", "Lactose-Free")
    val selectedPreferences = mutableStateOf<Set<String>>(emptySet())
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    navController: NavController,
    viewModel: CompleteProfileViewModel = viewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Adiciona scroll
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_foreground), // Use seu logo
                contentDescription = "Logo",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "COMPLETE YOUR PROFILE",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Seção de Upload de Foto
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Lógica para abrir a galeria */ }
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Upload Photo",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                        .padding(12.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Upload Photo",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de Texto
            OutlinedTextField(
                value = viewModel.dateOfBirth.value,
                onValueChange = { viewModel.dateOfBirth.value = it },
                label = { Text("Date of Birth (DD/MM/YYYY)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.gender.value,
                onValueChange = { viewModel.gender.value = it },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.weight.value,
                onValueChange = { viewModel.weight.value = it },
                label = { Text("Current Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Seção de Preferências Dietéticas
            Text(
                text = "Dietary Preferences",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                viewModel.dietaryPreferences.forEach { preference ->
                    val isSelected = viewModel.selectedPreferences.value.contains(preference)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            // Lógica para adicionar ou remover a preferência da lista
                            val currentSelected = viewModel.selectedPreferences.value.toMutableSet()
                            if (isSelected) {
                                currentSelected.remove(preference)
                            } else {
                                currentSelected.add(preference)
                            }
                            viewModel.selectedPreferences.value = currentSelected
                        },
                        label = { Text(preference) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão SAVE PROFILE
            Button(
                onClick = {
                    navController.navigate(Screen.Main.route) {
                        // Limpa toda a pilha de navegação até a WelcomeScreen
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(GradientStart, GradientEnd)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "SAVE PROFILE", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espaço no final
        }
    }
}