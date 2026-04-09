package com.david.quizuppro.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{

    @Serializable
    object SplashScreen : Screen()

    @Serializable
    object SignUpScreen : Screen()

    @Serializable
    object LoginScreen : Screen()
    @Serializable
    object CategorySelectionScreen : Screen()
    @Serializable
    data class UnitSelectionScreen(
        val categoryId: Int,
        val categoryName: String
    ) : Screen()

    @Serializable
    data class QuizScreen(
        val categoryId: Int,
        val categoryName: String,
        val unitId: Int,
        val difficulty: String
    ) : Screen()

    @Serializable
    object ProfileScreen : Screen()

    @Serializable
    object LeaderboardScreen : Screen()

    @Serializable
    data class ResultScreen(
        val score: Int,
        val totalQuestions: Int
    ) : Screen()

}
