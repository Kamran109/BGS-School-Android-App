package com.david.quizuppro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.quizuppro.model.Category
import com.david.quizuppro.model.Difficulty
import com.david.quizuppro.ui.theme.DarkBlue
import com.david.quizuppro.ui.theme.White
import com.david.quizuppro.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionScreen(
    onCategorySelected: (Int, Difficulty) -> Unit,
    onProfileClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
    viewModel: CategoryViewModel = viewModel()
) {
    val categories by viewModel.categories.collectAsState()
    var selectedDifficulty by remember { mutableStateOf(Difficulty.MEDIUM) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "QuizUp Pro",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                    IconButton(onClick = onLeaderboardClick) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Leaderboard"
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Difficulty Selector
            Text(
                text = "Select Difficulty",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DifficultyChip(
                    difficulty = Difficulty.EASY,
                    isSelected = selectedDifficulty == Difficulty.EASY,
                    onSelect = { selectedDifficulty = Difficulty.EASY }
                )
                DifficultyChip(
                    difficulty = Difficulty.MEDIUM,
                    isSelected = selectedDifficulty == Difficulty.MEDIUM,
                    onSelect = { selectedDifficulty = Difficulty.MEDIUM }
                )
                DifficultyChip(
                    difficulty = Difficulty.HARD,
                    isSelected = selectedDifficulty == Difficulty.HARD,
                    onSelect = { selectedDifficulty = Difficulty.HARD }
                )
            }

//            Text(
//                text = "Choose a Category",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize().background(White)
            ) {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategorySelected(category.id, selectedDifficulty) }
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultyChip(
    difficulty: Difficulty,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onSelect,
        label = { Text(difficulty.name) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    val gradientColors = when (category.id) {
        1 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        2 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        3 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        4 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        5 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        6 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        7 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        8 -> listOf(Color(0xFF364B65), Color(0xFF4A5F7A))
        else -> listOf(Color(0xFF667eea), Color(0xFF764ba2))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = category.icon,
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoryCardPrev() {
    CategorySelectionScreen(
        onCategorySelected = { _, _ -> },
        onProfileClick = { },
        onLeaderboardClick = { }
    )
}

