package com.david.quizuppro.model

data class Question(
        val id: Int,
        val question: String,
        val options: List<String>,
        val correctAnswerIndex: Int
    )
