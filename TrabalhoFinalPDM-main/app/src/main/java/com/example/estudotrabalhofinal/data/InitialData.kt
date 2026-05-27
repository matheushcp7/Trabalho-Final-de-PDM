package com.example.estudotrabalhofinal.data


object InitialData {
    fun getInitialFoods(): List<Food> {
        return listOf(
            Food(id = 1, name = "Maçã (Unidade Média)", calories = 95),
            Food(id = 2, name = "Banana (Unidade Média)", calories = 105),
            Food(id = 3, name = "Peito de Frango (100g)", calories = 165),
            Food(id = 4, name = "Arroz Branco Cozido (100g)", calories = 130),
            Food(id = 5, name = "Feijão Carioca Cozido (100g)", calories = 76),
            Food(id = 6, name = "Ovo Cozido (Unidade)", calories = 78),
            Food(id = 7, name = "Pão Francês (Unidade)", calories = 135),
            Food(id = 8, name = "Leite Integral (200ml)", calories = 122),
            Food(id = 9, name = "Queijo Minas Frescal (30g)", calories = 72),
            Food(id = 10, name = "Alface (100g)", calories = 15),
            Food(id = 11, name = "Tomate (100g)", calories = 18),
            Food(id = 12, name = "Batata Doce Cozida (100g)", calories = 86)
        )
    }
}