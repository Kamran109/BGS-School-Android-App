package com.david.quizuppro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.david.quizuppro.data.QuizRepository
import com.david.quizuppro.data.local.QuizDao
import com.david.quizuppro.data.local.QuizResultEntity
import com.david.quizuppro.data.local.UserPreferences
import com.david.quizuppro.data.remote.FirestoreRepository
import com.david.quizuppro.model.Difficulty
import com.david.quizuppro.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val score: Int = 0,
    val isQuizComplete: Boolean = false,
    val timeRemaining: Int = 30, // 30 seconds per question
    val difficulty: Difficulty = Difficulty.MEDIUM
)

class QuizViewModel(
    private val quizDao: QuizDao,
    private val firestoreRepository: FirestoreRepository,
    private val userPreferences: UserPreferences,
    private val externalScope: CoroutineScope
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentcategoryId: Int = 0

    fun loadQuiz(categoryId: Int, difficulty: Difficulty) {
        currentcategoryId = categoryId
        val questions = QuizRepository.getQuestionsForCategory(categoryId)
        // In a real app, we might filter questions by difficulty here
        // For now, difficulty affects the timer: Easy=30s, Medium=20s, Hard=10s
        val timePerQuestion = when(difficulty) {
            Difficulty.EASY -> 30
            Difficulty.MEDIUM -> 20
            Difficulty.HARD -> 10
        }

        _uiState.value = QuizUiState(
            questions = questions,
            difficulty = difficulty,
            timeRemaining = timePerQuestion
        )
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0 && !_uiState.value.isAnswerSubmitted) {
                delay(1000L)
                _uiState.value = _uiState.value.copy(
                    timeRemaining = _uiState.value.timeRemaining - 1
                )
            }
            if (_uiState.value.timeRemaining == 0 && !_uiState.value.isAnswerSubmitted) {
                // Time's up! Mark as wrong/unanswered and move on
                _uiState.value = _uiState.value.copy(
                    isAnswerSubmitted = true, // Lock interaction
                    selectedAnswerIndex = -1 // No answer selected
                )
                delay(2000)
                moveToNextQuestion()
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        if (_uiState.value.isAnswerSubmitted) return

        timerJob?.cancel() // Stop timer when answer selected

        val currentQuestion = _uiState.value.questions[_uiState.value.currentQuestionIndex]
        val isCorrect = answerIndex == currentQuestion.correctAnswerIndex

        _uiState.value = _uiState.value.copy(
            selectedAnswerIndex = answerIndex,
            isAnswerSubmitted = true,
            score = if (isCorrect) _uiState.value.score + 1 else _uiState.value.score
        )

        // Auto move to next question after 2 seconds
        viewModelScope.launch {
            delay(2000)
            moveToNextQuestion()
        }
    }

    private fun moveToNextQuestion() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1

        if (nextIndex < _uiState.value.questions.size) {
            val timePerQuestion = when(_uiState.value.difficulty) {
                Difficulty.EASY -> 30
                Difficulty.MEDIUM -> 20
                Difficulty.HARD -> 10
            }

            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = nextIndex,
                selectedAnswerIndex = null,
                isAnswerSubmitted = false,
                timeRemaining = timePerQuestion
            )
            startTimer()
        } else {
            _uiState.value = _uiState.value.copy(
                isQuizComplete = true
            )
            saveResult()
        }
    }

    private fun saveResult() {
        // Save to local DB (ViewModel scope is fine as it's quick)
        viewModelScope.launch {
            val result = QuizResultEntity(
                categoryId = currentcategoryId,
                score = _uiState.value.score,
                totalQuestions = _uiState.value.questions.size,
                difficulty = _uiState.value.difficulty
            )
            quizDao.insertQuizResult(result)
        }

        // Sync with Firestore (Use external scope to ensure completion even if user navigates away)
        externalScope.launch {
            val userId = userPreferences.getUserId()
            val username = userPreferences.getUsername()
            firestoreRepository.updateUserScore(userId, username, _uiState.value.score)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    fun resetQuiz() {
        timerJob?.cancel()
        _uiState.value = QuizUiState()
    }
}

class QuizViewModelFactory(
    private val quizDao: QuizDao,
    private val firestoreRepository: FirestoreRepository,
    private val userPreferences: UserPreferences,
    private val externalScope: CoroutineScope
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(quizDao, firestoreRepository, userPreferences, externalScope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
