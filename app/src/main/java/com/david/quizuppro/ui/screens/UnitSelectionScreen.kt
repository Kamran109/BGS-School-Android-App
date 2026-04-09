package com.david.quizuppro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.quizuppro.data.QuizRepository
import com.david.quizuppro.model.Unit
import com.david.quizuppro.ui.theme.DarkBlue
import com.david.quizuppro.ui.theme.NavyBlue
import com.david.quizuppro.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSelectionScreen(
    categoryId: Int,        // ➕ YE ADD KARO
    categoryName: String,
    onUnitSelected: (Int) -> kotlin.Unit,
    onBackPressed: () -> kotlin.Unit
) {
    val units = remember(categoryId) {
        QuizRepository.getUnitsForCategory(categoryId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoryName,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Units List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(units) { unit ->
                        UnitCard(
                            unit = unit,
                            onClick = { onUnitSelected(unit.id) }
                        )
                }
            }
        }
    }
}


@Composable
fun UnitCard(
    unit: Unit,
    onClick: () -> kotlin.Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(DarkBlue, NavyBlue)
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emoji Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unit.emoji,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Unit Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Unit ${unit.id}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Normal,
                        color = White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = unit.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${unit.questionCount} Questions",
                        style = MaterialTheme.typography.bodySmall,
                        color = White
                    )
                }

                // Arrow Icon
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Start",
                    tint = White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UnitCardPreview() {
    UnitSelectionScreen(
        categoryId = 1,
        categoryName = "Category Name",
        onUnitSelected = {},
        onBackPressed = {}
    )
}
