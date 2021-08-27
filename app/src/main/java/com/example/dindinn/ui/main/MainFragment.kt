package com.example.dindinn.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dindinn.ui.main.adapter.OrderListAdapter
import com.example.dindinn.ui.main.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dindinn.R
import dindinn.databinding.MainFragmentBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: MainFragmentBinding
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        bindObservers()

    }

    private fun initUI() {

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            itemAnimator = null
            adapter = OrderListAdapter(viewModel, viewLifecycleOwner).also {
                orderListAdapter = it
            }
        }
    }

    private fun bindObservers() {

        viewModel.orders.observe(viewLifecycleOwner, {
            orderListAdapter.submitList(it)
        })

        viewModel.openIngredient.observe(viewLifecycleOwner, {
            navToIngredientScreen()
        })

    }

    private fun navToIngredientScreen() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, IngredientFragment.newInstance(), "ingredient")
            ?.addToBackStack(null)
            ?.commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.orderList.forEach {
            it.stopTimer()
        }
    }


}
