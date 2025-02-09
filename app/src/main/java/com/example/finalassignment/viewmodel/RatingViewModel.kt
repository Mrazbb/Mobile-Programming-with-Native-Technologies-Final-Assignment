package com.example.finalassignment.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.RatingsApi
import com.example.finalassignment.model.UserRating
import kotlinx.coroutines.launch

class RatingViewModel: ViewModel() {

    val ratingsList = mutableStateListOf<UserRating>()

    init {
        fetchRatings()
    }

    // Fetches ratings from the API and updates the ratingsList.
    fun fetchRatings() {
        viewModelScope.launch {
            try {
                val apiRatings = RatingsApi.getInstance().getRatings()
                ratingsList.clear()
                ratingsList.addAll(apiRatings)
            } catch (e: Exception) {
                Log.d("ERROR", "Error fetching ratings: ${e.message}")
            }
        }
    }

    // Sends a new rating to the API via a POST request.
    fun submitRating(rating: UserRating) {
        viewModelScope.launch {
            try {
                val postedRating = RatingsApi.getInstance().postRating(rating)

                ratingsList.add(postedRating)
                fetchRatings()
                Log.d("RATING", "Rating posted successfully: $postedRating")
            } catch (e: Exception) {
                Log.d("ERROR", "Error posting rating: ${e.message}")
            }
        }
    }
}

