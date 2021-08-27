package com.example.dindinn.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dindinn.model.Ingredient
import com.example.dindinn.ui.main.adapter.IngredientPagerAdapter
import com.example.dindinn.ui.main.vm.IngredientViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dindinn.databinding.FragmentIngredientBinding

@AndroidEntryPoint
class IngredientFragment : Fragment() {

    companion object {
        fun newInstance() = IngredientFragment()
    }

    private lateinit var binding: FragmentIngredientBinding

    private val viewModel: IngredientViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        bindListener()

    }

    private fun initUI() {
        val ingredients = listOf(Ingredient.BENTO, Ingredient.MAIN, Ingredient.APPETIZER)
        binding.viewPager.apply {
            adapter = IngredientPagerAdapter(this@IngredientFragment, ingredients)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = ingredients[position].text
        }.attach()
    }

    private fun bindListener() {
        binding.searchEdit.doOnTextChanged { text, start, before, count ->
            viewModel.search(text.toString())
        }
    }
}
