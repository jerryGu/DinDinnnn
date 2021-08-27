package com.example.dindinn.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dindinn.ui.main.adapter.IngredientListAdapter
import com.example.dindinn.ui.main.vm.IngredientContentViewModel
import com.example.dindinn.ui.main.vm.IngredientViewModel
import dagger.hilt.android.AndroidEntryPoint
import dindinn.databinding.FragmentIngredientContentBinding

@AndroidEntryPoint
class IngredientContentFragment : Fragment() {

    companion object {
        private const val CATEGORY_ID = "categoryId"
        fun newInstance(categoryId: Int) = IngredientContentFragment().apply {
            arguments = Bundle().apply {
                putInt(CATEGORY_ID, categoryId)
            }
        }
    }

    private val viewModel: IngredientContentViewModel by viewModels()

    private val parentViewModel: IngredientViewModel by viewModels({ requireParentFragment() })

    private lateinit var binding: FragmentIngredientContentBinding
    private lateinit var ingredientListAdapter: IngredientListAdapter

    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt(CATEGORY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientContentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        bindObservers()

        viewModel.fetchIngredient(categoryId)

    }

    private fun initUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            itemAnimator = null
            adapter = IngredientListAdapter(viewLifecycleOwner).also {
                ingredientListAdapter = it
            }
        }
    }

    private fun bindObservers() {

        viewModel.ingredients.observe(viewLifecycleOwner, {
            ingredientListAdapter.submitList(it)
        })

        parentViewModel.keyword.observe(viewLifecycleOwner, {
            viewModel.fetchIngredient(categoryId, it)
        })


    }


}
