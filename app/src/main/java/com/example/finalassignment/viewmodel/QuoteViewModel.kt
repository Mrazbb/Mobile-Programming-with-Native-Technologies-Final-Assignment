package com.example.finalassignment.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.GetQuote
import com.example.finalassignment.model.QuotesApi

class QuoteViewModel: ViewModel() {

    val quotes = mutableStateListOf<GetQuote>()

    suspend fun fetchQuotesSuspend() {
        try {
            val apiQuotes = QuotesApi.getInstance().getQuotes()
            quotes.clear()
            quotes.addAll(apiQuotes)
        } catch (e: Exception) {
            Log.d("ERROR", "Error fetching quotes: ${e.message}")
        }
    }
}


