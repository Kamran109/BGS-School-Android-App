package com.david.quizuppro.viewmodel

import androidx.lifecycle.ViewModel
import com.david.quizuppro.data.local.PrefsHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val prefs: PrefsHelper  // ← Add karo
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onIdChange(id: String) {
        _uiState.value = _uiState.value.copy(studentId = id, error = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    fun login(onSuccess: () -> Unit) {
        val id = uiState.value.studentId
        val pwd = uiState.value.password

        if (id.isBlank() || pwd.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Please enter ID and Password")
            return
        }

        if (id == "admin" && pwd == "admin") {
            prefs.setLoggedIn(true)  // ← Yeh add karo ✅
            onSuccess()
        } else {
             _uiState.value = _uiState.value.copy(error = "Invalid Credentials")
        }
    }
}

data class AuthUiState(
    val studentId: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
