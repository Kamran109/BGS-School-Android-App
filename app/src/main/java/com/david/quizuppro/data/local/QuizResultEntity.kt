package com.david.quizuppro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.david.quizuppro.model.Difficulty

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val score: Int,
    val totalQuestions: Int,
    val difficulty: Difficulty,
    val timestamp: Long = System.currentTimeMillis()
)
