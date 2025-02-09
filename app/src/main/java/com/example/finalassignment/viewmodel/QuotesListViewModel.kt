package com.example.finalassignment.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.finalassignment.model.UserRating
import com.example.finalassignment.model.RatingsApi
import kotlinx.coroutines.launch

class QuotesListViewModel(private val quoteViewModel: QuoteViewModel, private val ratingViewModel: RatingViewModel,) : ViewModel() {


    // IS LOADING
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    // error message
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> get() = _errorMessage

    // show quotes
    private val _showQuotes = mutableStateOf(false)
    val showQuotes: State<Boolean> get() = _showQuotes


    // Now initialize is responsible for waiting for data to load.
    fun initialize() {
        viewModelScope.launch {

            // Await both API calls
            try {
                quoteViewModel.fetchQuotesSuspend()
                ratingViewModel.fetchRatingsSuspend()
                if (quoteViewModel.quotes != null) {
                    _showQuotes.value = true
                }


            } catch (e: Exception) {
                _errorMessage.value = "Error loading quotes: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
                _showQuotes.value = true
            }
        }
    }

}