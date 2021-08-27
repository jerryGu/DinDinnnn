package com.example.dindinn.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dindinn.model.Ingredient

class IngredientPagerAdapter(
    fragment: Fragment,
    private val ingredients: List<Ingredient>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = ingredients.size

    override fun createFragment(position: Int): Fragment {
        return ingredients[position].getItem()
    }
}
