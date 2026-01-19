package com.david.quizuppro.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.quizuppro.model.Difficulty
import com.david.quizuppro.model.Question
import com.david.quizuppro.viewmodel.QuizViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreenPre() {
    QuizScreen(
        categoryId = 1,
        categoryName = "Category",
        difficulty = Difficulty.MEDIUM,
        onBackPressed = { },
        onQuizComplete = { _, _ -> },
        viewModel = viewModel()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    categoryId: Int,
    categoryName: String,
    difficulty: Difficulty,
    onBackPressed: () -> Unit,
    onQuizComplete: (Int, Int) -> Unit,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(categoryId) {
        viewModel.loadQuiz(categoryId, difficulty)
    }

    LaunchedEffect(uiState.isQuizComplete) {
        if (uiState.isQuizComplete) {
            onQuizComplete(uiState.score, uiState.questions.size)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = categoryName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = difficulty.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(end = 16.dp)) {
                        CircularProgressIndicator(
                            progress = { uiState.timeRemaining.toFloat() / (if (difficulty == Difficulty.HARD) 10 else if (difficulty == Difficulty.MEDIUM) 20 else 30) },
                            modifier = Modifier.size(36.dp),
                            color = if (uiState.timeRemaining <= 5) Color.Red else MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = "${uiState.timeRemaining}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (uiState.questions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Progress indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${uiState.currentQuestionIndex + 1}/${uiState.questions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Score: ${uiState.score}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                LinearProgressIndicator(
                    progress = { (uiState.currentQuestionIndex + 1).toFloat() / uiState.questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )

                Spacer(modifier = Modifier.height(32.dp))

                val currentQuestion = uiState.questions[uiState.currentQuestionIndex]

                QuestionCard(
                    question = currentQuestion,
                    selectedAnswerIndex = uiState.selectedAnswerIndex,
                    isAnswerSubmitted = uiState.isAnswerSubmitted,
                    onAnswerSelected = { index ->
                        viewModel.selectAnswer(index)
                    }
                )
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: Question,
    selectedAnswerIndex: Int?,
    isAnswerSubmitted: Boolean,
    onAnswerSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Question text
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = question.question,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Answer options
        question.options.forEachIndexed { index, option ->
            AnswerOption(
                text = option,
                index = index,
                isSelected = selectedAnswerIndex == index,
                isCorrect = index == question.correctAnswerIndex,
                isAnswerSubmitted = isAnswerSubmitted,
                onClick = { onAnswerSelected(index) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    index: Int,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswerSubmitted: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isAnswerSubmitted && isSelected && isCorrect -> Color(0xFF4CAF50) // Green
            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF44336) // Red
            isAnswerSubmitted && isCorrect -> Color(0xFF4CAF50) // Show correct answer
            isSelected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        label = "backgroundColor"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            isAnswerSubmitted && isSelected && isCorrect -> Color(0xFF2E7D32)
            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFC62828)
            isAnswerSubmitted && isCorrect -> Color(0xFF2E7D32)
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline
        },
        label = "borderColor"
    )

    val textColor = when {
        isAnswerSubmitted && (isCorrect || isSelected) -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isAnswerSubmitted) { onClick() }
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when {
                            isAnswerSubmitted && (isCorrect || isSelected) -> Color.White.copy(alpha = 0.3f)
                            else -> MaterialTheme.colorScheme.primaryContainer
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ('A' + index).toString(),
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isAnswerSubmitted && (isCorrect || isSelected) -> Color.White
                        else -> MaterialTheme.colorScheme.onPrimaryContainer
                    }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}
