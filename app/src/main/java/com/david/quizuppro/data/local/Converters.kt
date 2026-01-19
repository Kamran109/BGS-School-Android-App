package com.david.quizuppro.data.local

import androidx.room.TypeConverter
import com.david.quizuppro.model.Difficulty

class Converters {
    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String {
        return difficulty.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }
}
