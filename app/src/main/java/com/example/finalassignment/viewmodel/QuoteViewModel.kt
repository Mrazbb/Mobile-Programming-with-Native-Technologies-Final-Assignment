package com.example.finalassignment.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.GetQuote
import com.example.finalassignment.model.QuotesApi
import kotlinx.coroutines.launch

class QuoteViewModel: ViewModel() {

    val getQuotes = mutableStateListOf<GetQuote>()

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            var quotesApi: QuotesApi? = null
            try {
                quotesApi = QuotesApi.getInstance()
                val apiQuotes = quotesApi.getQuotes()


                getQuotes.clear()
                getQuotes.addAll(apiQuotes)

            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
            }
        }
    }
}


