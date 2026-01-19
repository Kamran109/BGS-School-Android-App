package com.david.quizuppro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllQuizResults(): Flow<List<QuizResultEntity>>

    @Query("SELECT COUNT(*) FROM quiz_results")
    fun getTotalQuizzesTaken(): Flow<Int>

    @Query("SELECT AVG(cast(score as FLOAT) / totalQuestions * 100) FROM quiz_results")
    fun getAverageScore(): Flow<Float?>
}
