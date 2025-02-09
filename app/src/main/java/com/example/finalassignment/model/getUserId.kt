package com.example.finalassignment.model

import android.content.Context
import java.util.UUID

fun getUserId(context: Context): String {
    // Open (or create) a SharedPreferences file named "user_prefs"
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Check if the user ID already exists
    var userId = sharedPreferences.getString("USER_ID", null)

    // If not, generate a new one and store it
    if (userId == null) {
        userId = UUID.randomUUID().toString()
        sharedPreferences.edit().putString("USER_ID", userId).apply()
    }

    return userId
}