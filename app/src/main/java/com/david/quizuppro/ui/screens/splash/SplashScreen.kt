package com.david.quizuppro.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.david.quizuppro.R
import com.david.quizuppro.data.local.PrefsHelper
import com.david.quizuppro.navigation.Screen
import com.david.quizuppro.ui.screens.components.localTextSize
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
//    val space = LocalSpace.current
    val textSize = localTextSize.current
    val logoScale = remember { Animatable(0f) }

    val prefs = PrefsHelper(context)

    val interFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )

    LaunchedEffect(true)
    {
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        delay(1500)
        when {
            prefs.isLoggedIn() -> {
                navController.navigate(Screen.CategorySelectionScreen) {
                    popUpTo(Screen.SplashScreen) { inclusive = true }
                }
            }
            else -> {
                navController.navigate(Screen.LoginScreen) {
                    popUpTo(Screen.SplashScreen) { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .scale(logoScale.value)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.expenseTracker),
                fontFamily = interFont,
                fontWeight = FontWeight.Bold,
                fontSize = textSize.size24,
                color = colorResource(id = R.color.black)
            )
            Text(
                text = stringResource(id = R.string.smartBudgetingTrackingMadeEasy),
                fontFamily = interFont,
                fontWeight = FontWeight.Normal,
                fontSize = textSize.size16,
                color = colorResource(id = R.color.text_gray)
            )
        }
    }
}
