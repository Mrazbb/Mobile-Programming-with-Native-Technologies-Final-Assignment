package com.example.finalassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class QuotesHomeViewModelFactory(
    private val quoteViewModel: QuoteViewModel,
    private val ratingViewModel: RatingViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesHomeViewModel::class.java)) {
            return QuotesHomeViewModel(quoteViewModel, ratingViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
