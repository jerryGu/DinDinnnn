package com.example.dindinn.model

import androidx.fragment.app.Fragment
import com.example.dindinn.ui.main.IngredientContentFragment

enum class Ingredient(val text: String, val categoryId: Int) {


    BENTO("Bento", 1),


    MAIN("Main", 2),

    APPETIZER("Appetizer", 3);


    fun getItem(): Fragment = IngredientContentFragment.newInstance(categoryId)
}



