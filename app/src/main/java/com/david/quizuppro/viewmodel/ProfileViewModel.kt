package com.david.quizuppro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.david.quizuppro.data.local.QuizDao
import com.david.quizuppro.data.local.QuizResultEntity
import com.david.quizuppro.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(
    private val quizDao: QuizDao,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _username = MutableStateFlow(userPreferences.getUsername())
    val username: StateFlow<String> = _username.asStateFlow()

    fun updateUsername(newName: String) {
        userPreferences.setUsername(newName)
        _username.value = newName
    }

    val totalQuizzes: StateFlow<Int> = quizDao.getTotalQuizzesTaken()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val averageScore: StateFlow<Float?> = quizDao.getAverageScore()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    val history: StateFlow<List<QuizResultEntity>> = quizDao.getAllQuizResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

class ProfileViewModelFactory(
    private val quizDao: QuizDao,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(quizDao, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
