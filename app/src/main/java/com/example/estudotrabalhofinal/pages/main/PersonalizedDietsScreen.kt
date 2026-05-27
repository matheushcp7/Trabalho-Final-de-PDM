package com.example.estudotrabalhofinal.pages.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estudotrabalhofinal.pages.theme.TextPrimary

// Estrutura de dados para o conteúdo educativo
data class EducationalContent(
    val title: String,
    val icon: ImageVector,
    val description: String
)

// Lista com os conteúdos
val educationalContentList = listOf(
    EducationalContent(
        title = "O que são Calorias?",
        icon = Icons.Default.Info,
        description = "Uma caloria é uma unidade de energia. Em nutrição, calorias referem-se à energia que as pessoas obtêm da comida e da bebida que consomem, e à energia que usam na atividade física. Um balanço calórico (consumir o mesmo que gasta) mantém o peso. Consumir mais leva ao ganho de peso, e consumir menos, à perda."
    ),
    EducationalContent(
        title = "Guia Rápido de Suplementos",
        icon = Icons.Default.Info,
        description = "Suplementos podem complementar sua dieta, mas não substituem uma alimentação saudável.\n\n• Whey Protein: Proteína de alta qualidade, ideal para recuperação muscular pós-treino.\n• Creatina: Melhora a força e o desempenho em exercícios de alta intensidade.\n• Multivitamínicos: Garantem que você não tenha deficiência de micronutrientes essenciais."
    ),
    EducationalContent(
        title = "Macronutrientes: O Básico",
        icon = Icons.Default.Info,
        description = "Os macronutrientes são os nutrientes que o corpo precisa em maiores quantidades.\n\n• Proteínas: Essenciais para construir e reparar tecidos (músculos, pele, etc.). Fontes: carnes, ovos, laticínios, leguminosas.\n• Carboidratos: A principal fonte de energia do corpo. Fontes: pães, massas, arroz, frutas.\n• Gorduras: Importantes para a produção de hormônios e absorção de vitaminas. Fontes: abacate, nozes, azeite."
    )
)

@Composable
fun PersonalizedDietsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Título
        item {
            Text(
                text = "YOUR PERSONALIZED DIETS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        // Seção "TODAY'S PLAN" (Placeholder)
        item {
            // Card com Row para o progresso e Column/Row para as refeições
            // Ex: Breakfast, Lunch, Dinner cards
        }

        // Botão "LOG TODAY'S MEALS" (Placeholder)
        item {
            // Botão de gradiente
        }

        // --- SEÇÃO DE CONTEÚDO EDUCATIVO ---
        item {
            Column {
                Text(
                    text = "Aprenda Sobre Nutrição",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Divider()
            }
        }

        items(educationalContentList) { content ->
            EducationalContentCard(content = content)
        }
    }
}

@Composable
fun EducationalContentCard(content: EducationalContent) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = content.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Recolher" else "Expandir"
                )
            }

            // Conteúdo expansível com animação
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}