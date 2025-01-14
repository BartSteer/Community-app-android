package com.example.communityapp.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.Date

enum class PointsTransactionType(val type: String) {
    INITIAL("initial"),
    ACHIEVEMENT("achievement"),
    ENGAGEMENT("engagement"),
    PURCHASE("purchase"),
    REWARD("reward"),
    OTHER("other");

    companion object {
        fun fromString(type: String?): PointsTransactionType {
            return values().find { it.type == type } ?: OTHER
        }
    }
}

@IgnoreExtraProperties
data class PointsTransaction(
    val userId: String = "",
    val amount: Int = 0,
    val type: PointsTransactionType = PointsTransactionType.OTHER,
    val description: String = "",
    val timestamp: Date = Date()
) {
    companion object {
        fun fromFirestore(data: Map<String, Any>): PointsTransaction {
            return PointsTransaction(
                userId = data["userId"] as? String ?: "",
                amount = data["amount"] as? Int ?: 0,
                type = PointsTransactionType.valueOf(data["type"] as? String ?: PointsTransactionType.OTHER.name),
                description = data["description"] as? String ?: "",
                timestamp = (data["timestamp"] as? Timestamp)?.toDate() ?: Date()
            )
        }
    }

    @Exclude
    fun toFirestore(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "amount" to amount,
            "type" to type.type,
            "description" to description,
            "timestamp" to Timestamp(timestamp)
        )
    }
}

