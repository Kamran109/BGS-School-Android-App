package com.david.quizuppro.navigation


sealed class Screen(val route: String) {
    object CategorySelection : Screen("category_selection")
    object Quiz : Screen("quiz/{categoryId}/{categoryName}/{difficulty}") {
        fun createRoute(categoryId: Int, categoryName: String, difficulty: String) = "quiz/$categoryId/$categoryName/$difficulty"
    }
    object Result : Screen("result/{score}/{totalQuestions}") {
        fun createRoute(score: Int, totalQuestions: Int) = "result/$score/$totalQuestions"
    }
    object Profile : Screen("profile")
    object Leaderboard : Screen("leaderboard")
}
