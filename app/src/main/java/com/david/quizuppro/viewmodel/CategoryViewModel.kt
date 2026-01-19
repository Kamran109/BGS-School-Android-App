package com.david.quizuppro.viewmodel

import androidx.lifecycle.ViewModel
import com.david.quizuppro.data.QuizRepository
import com.david.quizuppro.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel: ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())

    val categories: StateFlow<List<Category>> = _categories.asStateFlow()
    init {
        loadCategories()
    }

    private fun loadCategories(){
        _categories.value = QuizRepository.categories

    }

}