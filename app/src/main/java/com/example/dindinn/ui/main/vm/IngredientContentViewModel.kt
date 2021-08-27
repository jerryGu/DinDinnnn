package com.example.dindinn.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dindinn.entity.IngredientResponse
import com.example.dindinn.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientContentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _ingredients = MutableLiveData<List<IngredientResponse>>()
    val ingredients: LiveData<List<IngredientResponse>> = _ingredients


    fun fetchIngredient(id: Int, keyword: String? = null) {
        viewModelScope.launch {
            repository.getIngredients(id, keyword).collect {
                _ingredients.value = it
            }
        }
    }
}
