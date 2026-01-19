package com.david.quizuppro.model

data class LeaderboardEntry(
    val id: String = "",
    val username: String = "",
    val totalScore: Int = 0,
    val quizzesPlayed: Int = 0
)
