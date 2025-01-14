package com.example.communityapp.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import android.graphics.Bitmap
import java.util.Date

enum class UserId(val value: String) {
    USER1("user_001"),
    USER2("user_002"),
    USER3("user_003"),
    USER4("user_004"),
    USER5("user_005");

    companion object {
        var current: UserId = USER4
    }
}

data class UserModel(
    val id: String = UserId.current.value,
    var name: String,
    var email: String,
    var nationality: Nationality,
    var location: String,
    var bio: String,
    var profileImage: Bitmap? = null,
    var achievementIds: List<String> = listOf(),
    var points: Int = 0,
    var pointsHistory: List<PointsTransaction> = listOf()
) {

    companion object {
        fun initialUser(): UserModel {
            return UserModel(
                name = "John Doe",
                email = "john.doe@example.com",
                nationality = Nationality.BRITISH,
                location = "London, United Kingdom",
                bio = "Explorer and traveler, passionate about discovering new cultures and meeting people from around the world. Always eager to learn about different traditions and share experiences.",
                points = 50,
                pointsHistory = listOf(
                    PointsTransaction(
                        userId = UserId.current.value,
                        amount = 50,
                        type = PointsTransactionType.INITIAL,
                        description = "Welcome bonus points",
                        timestamp = Date()
                    )
                )
            )
        }

        fun fromFirestore(snapshot: DocumentSnapshot): UserModel {
            val data = snapshot.data ?: return UserModel(name = "", email = "", nationality = Nationality.BRITISH, location = "", bio = "")
            val pointsHistory = (data["pointsHistory"] as? List<Map<String, Any>>)?.map {
                PointsTransaction.fromFirestore(it)
            } ?: listOf()

            return UserModel(
                id = data["id"] as? String ?: "",
                name = data["name"] as? String ?: "",
                email = data["email"] as? String ?: "",
                nationality = Nationality.valueOf((data["nationality"] as? String ?: "").uppercase()),
                location = data["location"] as? String ?: "",
                bio = data["bio"] as? String ?: "",
                achievementIds = data["achievementIds"] as? List<String> ?: listOf(),
                points = (data["points"] as? Long)?.toInt() ?: 0,
                pointsHistory = pointsHistory
            )
        }
    }

    fun toFirestore(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "nationality" to nationality.name.lowercase(),
            "location" to location,
            "bio" to bio,
            "achievementIds" to achievementIds,
            "points" to points,
            "pointsHistory" to pointsHistory.map { it.toFirestore() }
        )
    }
}

// Assuming PointsTransaction and Nationality classes are also defined similarly in Kotlin.
