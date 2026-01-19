package com.david.quizuppro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.david.quizuppro.QuizApplication
import com.david.quizuppro.data.QuizRepository
import com.david.quizuppro.data.local.UserPreferences
import com.david.quizuppro.data.remote.FirestoreRepository
import com.david.quizuppro.model.Difficulty
import com.david.quizuppro.ui.screens.CategorySelectionScreen
import com.david.quizuppro.ui.screens.LeaderboardScreen
import com.david.quizuppro.ui.screens.ProfileScreen
import com.david.quizuppro.ui.screens.QuizScreen
import com.david.quizuppro.ui.screens.ResultScreen
import com.david.quizuppro.viewmodel.LeaderboardViewModel
import com.david.quizuppro.viewmodel.LeaderboardViewModelFactory
import com.david.quizuppro.viewmodel.ProfileViewModel
import com.david.quizuppro.viewmodel.ProfileViewModelFactory
import com.david.quizuppro.viewmodel.QuizViewModel
import com.david.quizuppro.viewmodel.QuizViewModelFactory


//@Preview(showBackground = true)
@Composable
fun QuizNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val database = application.database
    val userPreferences = UserPreferences(context)
    val firestoreRepository = FirestoreRepository()

    NavHost(
        navController = navController,
        startDestination = Screen.CategorySelection.route
    ) {
        composable(Screen.CategorySelection.route) {
            CategorySelectionScreen(
                onCategorySelected = { categoryId, difficulty ->
                    val category = QuizRepository.categories.find { it.id == categoryId }
                    category?.let {
                        navController.navigate(
                            Screen.Quiz.createRoute(categoryId, it.name, difficulty.name)
                        )
                    }
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onLeaderboardClick = {
                    navController.navigate(Screen.Leaderboard.route)
                }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("categoryName") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 1
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val difficultyString = backStackEntry.arguments?.getString("difficulty") ?: "MEDIUM"
            val difficulty = try {
                Difficulty.valueOf(difficultyString)
            } catch (e: Exception) {
                Difficulty.MEDIUM
            }

            val viewModel: QuizViewModel = viewModel(
                factory = QuizViewModelFactory(
                    database.quizDao(),
                    firestoreRepository,
                    userPreferences,
                    application.applicationScope
                )
            )

            QuizScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                difficulty = difficulty,
                onBackPressed = {
                    navController.popBackStack()
                },
                onQuizComplete = { score, totalQuestions ->
                    navController.navigate(
                        Screen.Result.createRoute(score, totalQuestions)
                    ) {
                        popUpTo(Screen.CategorySelection.route)
                    }
                },
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("totalQuestions") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 10

            ResultScreen(
                score = score,
                totalQuestions = totalQuestions,
                onBackToHome = {
                    navController.navigate(Screen.CategorySelection.route) {
                        popUpTo(Screen.CategorySelection.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(database.quizDao(), userPreferences)
            )

            ProfileScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }

        composable(Screen.Leaderboard.route) {
            val viewModel: LeaderboardViewModel = viewModel(
                factory = LeaderboardViewModelFactory()
            )

            LeaderboardScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
    }
}
