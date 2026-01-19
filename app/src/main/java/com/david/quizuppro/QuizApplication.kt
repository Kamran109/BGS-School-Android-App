package com.david.quizuppro

import android.app.Application
import com.david.quizuppro.data.local.QuizDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuizApplication : Application() {
    val database by lazy { QuizDatabase.getDatabase(this) }
    val applicationScope = CoroutineScope(SupervisorJob())
}
