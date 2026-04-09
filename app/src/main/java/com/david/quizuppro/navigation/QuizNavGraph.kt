package com.david.quizuppro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.david.quizuppro.QuizApplication
import com.david.quizuppro.data.QuizRepository
import com.david.quizuppro.data.local.PrefsHelper
import com.david.quizuppro.data.remote.FirestoreRepository
import com.david.quizuppro.model.Difficulty
import com.david.quizuppro.ui.screens.LoginScreen
import com.david.quizuppro.ui.screens.UnitSelectionScreen
import com.david.quizuppro.ui.screens.CategorySelectionScreen
import com.david.quizuppro.ui.screens.ProfileScreen
import com.david.quizuppro.ui.screens.QuizScreen
import com.david.quizuppro.ui.screens.LeaderboardScreen
import com.david.quizuppro.ui.screens.ResultScreen
import com.david.quizuppro.ui.screens.SignUpScreen
import com.david.quizuppro.ui.screens.splash.SplashScreen
import com.david.quizuppro.viewmodel.AuthViewModel
import com.david.quizuppro.viewmodel.LeaderboardViewModel
import com.david.quizuppro.viewmodel.LeaderboardViewModelFactory
import com.david.quizuppro.viewmodel.ProfileViewModel
import com.david.quizuppro.viewmodel.ProfileViewModelFactory
import com.david.quizuppro.viewmodel.QuizViewModel
import com.david.quizuppro.viewmodel.QuizViewModelFactory

@Composable
fun QuizNavGraph(
    navController: NavHostController
) {

    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val database = application.database
    val userPreferences = PrefsHelper(context)
    val firestoreRepository = FirestoreRepository()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen
    ){

        composable<Screen.SplashScreen> {
            SplashScreen(navController)
        }

/*<--------------------------------LoginScreen--------------------------------------------------------->*/

        composable<Screen.LoginScreen> {
            LoginScreen(
                navController = navController,
                viewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            AuthViewModel(userPreferences)
                        }
                    }
                )
            )
        }

/*<--------------------------------SignUpScreen--------------------------------------------------------->*/

        composable<Screen.SignUpScreen> {
            SignUpScreen()
        }

/*<--------------------------------CategorySelectionScreen--------------------------------------------->*/

        composable<Screen.CategorySelectionScreen> {
            CategorySelectionScreen(
                onCategorySelected = { categoryId, difficulty ->
                    val category = QuizRepository.categories.find { it.id == categoryId }
                    category?.let {
                        navController.navigate(
                            Screen.UnitSelectionScreen(
                                categoryId = categoryId,
                                categoryName = it.name
                            )
                        )
                    }
                },
                onProfileClick = {
                    navController.navigate(Screen.ProfileScreen)
                },
                onLeaderboardClick = {
                    navController.navigate(Screen.LeaderboardScreen)
                }
            )
        }

/*<--------------------------------UnitSelectionScreen--------------------------------------------------------->*/

        composable <Screen.UnitSelectionScreen>{ backStackEntry ->
            val screen: Screen.UnitSelectionScreen = backStackEntry.toRoute()
            UnitSelectionScreen(
                categoryId = screen.categoryId,
                categoryName = screen.categoryName,
                onUnitSelected = { unitId ->
                    navController.navigate(
                        Screen.QuizScreen(
                            categoryId = screen.categoryId,
                            categoryName = screen.categoryName,
                            unitId = unitId,
                            difficulty = "MEDIUM"
                        )
                    )
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

/*<--------------------------------QuizScreen--------------------------------------------------------->*/

        composable<Screen.QuizScreen> { backStackEntry ->
            val screen: Screen.QuizScreen = backStackEntry.toRoute()
            val difficulty = try {
                Difficulty.valueOf(screen.difficulty)
            }catch (e: Exception){
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
                categoryId = screen.categoryId,
                categoryName = screen.categoryName,
                unitId = screen.unitId,
                difficulty = difficulty,
                onBackPressed = {
                    navController.popBackStack()
                },
                onQuizComplete = { score, totalQuestions ->
                    navController.navigate(Screen.ResultScreen(
                        score = score,
                        totalQuestions = totalQuestions
                    ))

                },
                viewModel = viewModel
            )
        }

/*<--------------------------------ResultScreen--------------------------------------------------------->*/

        composable <Screen.ResultScreen> { backStackEntry ->
            val screen: Screen.ResultScreen = backStackEntry.toRoute()
            ResultScreen(
                score = screen.score,
                totalQuestions = screen.totalQuestions,
                onBackToHome = {
                    navController.navigate(Screen.CategorySelectionScreen){
                        popUpTo (Screen.CategorySelectionScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }

/*<--------------------------------ProfileScreen--------------------------------------------------------->*/

        composable <Screen.ProfileScreen>{
            val viewModel = viewModel<ProfileViewModel>(
                factory = ProfileViewModelFactory(database.quizDao(), userPreferences)
            )
            ProfileScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )

        }

/*<--------------------------------LeaderboardScreen--------------------------------------------------------->*/

        composable <Screen.LeaderboardScreen>{
            val viewModel = viewModel<LeaderboardViewModel>(
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