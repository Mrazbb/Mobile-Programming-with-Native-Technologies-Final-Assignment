package com.example.finalassignment.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuotesListViewModelFactory(
    private val quoteViewModel: QuoteViewModel,
    private val ratingViewModel: RatingViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesListViewModel::class.java)) {
            return QuotesListViewModel(quoteViewModel, ratingViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}