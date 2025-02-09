package com.example.finalassignment.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.UserRating
import com.example.finalassignment.model.RatingsApi
import kotlinx.coroutines.launch

class QuotesHomeViewModel(private val quoteViewModel: QuoteViewModel, private val ratingViewModel: RatingViewModel) : ViewModel() {

    // Holds the current quote id
    private val _currentQuoteId = mutableStateOf<Number?>(1)
    val currentQuoteId: State<Number?> get() = _currentQuoteId

    // Holds the current selected rating
    private val _selectedRating = mutableStateOf(0)
    val selectedRating: State<Int> get() = _selectedRating

    // IS LOADING
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    // error message
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> get() = _errorMessage


    // Now initialize is responsible for waiting for data to load.
    fun initialize(initialQuoteId: Number?) {
        viewModelScope.launch {

            // Await both API calls
            try {
                quoteViewModel.fetchQuotesSuspend()
                ratingViewModel.fetchRatingsSuspend()
            } catch (e: Exception) {
                _errorMessage.value = "Error loading quotes: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }

            val quotes = quoteViewModel.quotes
            val ratings = ratingViewModel.ratings

            if (initialQuoteId != null) {
                _currentQuoteId.value = initialQuoteId
            } else if (quotes.isNotEmpty()) {
                _currentQuoteId.value = quotes.first().id
            }

            // Now that ratings have been loaded, update the current rating.
            val currentRating = ratings.find { it.quoteid == _currentQuoteId.value }
            _selectedRating.value = currentRating?.rating ?: 0
        }
    }

    fun moveToNextQuote() {
        val quotes = quoteViewModel.quotes
        val ratings = ratingViewModel.ratings


        val currentIndex = quotes.indexOfFirst { it.id == _currentQuoteId.value }
        if (currentIndex != -1) {
            val nextIndex = if (currentIndex < quotes.lastIndex) currentIndex + 1 else 0
            val quote = quotes[nextIndex]
            val currentRating = ratings.find { it.quoteid == quote?.id }

            _currentQuoteId.value = quote.id
            if (currentRating != null ) {
                _selectedRating.value = currentRating.rating
            } else {
                _selectedRating.value = 0
            }

        }
    }

    fun updateRating(newRating: Int, quoteId: Int) {
        _selectedRating.value = newRating
        val userRating = UserRating(
            id = 0, // or use a suitable default if the backend auto-generates the ID
            userid = RatingsApi.userid, // Ensure the user ID is initialized
            quoteid = quoteId,
            rating = newRating
        )
        ratingViewModel.submitRating(userRating)
    }
}