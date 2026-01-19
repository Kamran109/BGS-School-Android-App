package com.david.quizuppro.data.remote

import android.util.Log
import com.david.quizuppro.model.LeaderboardEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val leaderboardCollection = firestore.collection("leaderboard")

    fun getLeaderboard(): Flow<List<LeaderboardEntry>> = callbackFlow {
        val subscription = leaderboardCollection
            .orderBy("totalScore", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirestoreRepository", "Error fetching leaderboard", error)
                    trySend(emptyList()) // Send empty list on error
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val entries = snapshot.toObjects(LeaderboardEntry::class.java)
                    trySend(entries)
                }
            }

        awaitClose { subscription.remove() }
    }

    suspend fun updateUserScore(userId: String, username: String, scoreToAdd: Int) {
        try {
            val docRef = leaderboardCollection.document(userId)
            val snapshot = docRef.get().await()

            if (snapshot.exists()) {
                val currentScore = snapshot.getLong("totalScore") ?: 0
                val currentGames = snapshot.getLong("quizzesPlayed") ?: 0
                
                docRef.update(
                    mapOf(
                        "totalScore" to (currentScore + scoreToAdd),
                        "quizzesPlayed" to (currentGames + 1),
                        "username" to username // Update username in case it changed
                    )
                ).await()
            } else {
                val newEntry = LeaderboardEntry(
                    id = userId,
                    username = username,
                    totalScore = scoreToAdd,
                    quizzesPlayed = 1
                )
                docRef.set(newEntry).await()
            }
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error updating score", e)
        }
    }
}
