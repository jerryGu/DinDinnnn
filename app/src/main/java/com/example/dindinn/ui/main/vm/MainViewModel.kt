package com.example.dindinn.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dindinn.model.Addon
import com.example.dindinn.model.Order
import com.example.dindinn.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _openIngredient = MutableLiveData<Unit>()
    val openIngredient: LiveData<Unit> = _openIngredient

    lateinit var orderList: List<Order>

    init {

        viewModelScope.launch {
            repository.getOrders().collect {
                it.mapIndexed { index, orderResponse ->
                    Order(
                        id = orderResponse.id,
                        title = orderResponse.title,
                        quantity = orderResponse.quantity,
                        addon = orderResponse.addon.map {
                            Addon(it.id, it.title, it.quantity)
                        }
                    ).apply {
                        // set alert time & expired time for test
                        this.alerted_at = this.created_at + (index + 1) * 60 * 1000
                        this.expired_at =
                            this.alerted_at + 60 * 1000 // expired time is 1 min longer than alert time.
                    }
                }.let {
                    orderList = it

                    _orders.value = orderList
                }
            }
        }
    }

    fun orderClicked(id: Int, expired: Boolean) {
        if (expired) { // remove this order
            val mutableList = orderList.toMutableList()
            mutableList.removeIf {
                it.id == id
            }

            orderList = mutableList.toList()
            _orders.value = orderList

        } else {
            // go to ingredient screen
            _openIngredient.value = Unit
        }
    }
}
