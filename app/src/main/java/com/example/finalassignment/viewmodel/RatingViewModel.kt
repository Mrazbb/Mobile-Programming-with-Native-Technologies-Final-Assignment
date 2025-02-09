package com.example.finalassignment.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.RatingsApi
import com.example.finalassignment.model.UserRating
import kotlinx.coroutines.launch

class RatingViewModel: ViewModel() {

    val ratings = mutableStateListOf<UserRating>()

    // Fetches ratings from the API and updates the ratingsList.
    suspend fun fetchRatingsSuspend() {
        try {
            val apiRatings = RatingsApi.getInstance().getRatings()
            ratings.clear()
            ratings.addAll(apiRatings)
        } catch (e: Exception) {
            Log.d("ERROR", "Error fetching ratings: ${e.message}")
        }
    }

    // Sends a new rating to the API via a POST request.
    fun submitRating(rating: UserRating) {
        viewModelScope.launch {
            try {
                val postedRating = RatingsApi.getInstance().postRating(rating)
                fetchRatingsSuspend()
                Log.d("RATING", "Rating posted successfully: $postedRating")
            } catch (e: Exception) {
                Log.d("ERROR", "Error posting rating: ${e.message}")
            }
        }
    }
}

