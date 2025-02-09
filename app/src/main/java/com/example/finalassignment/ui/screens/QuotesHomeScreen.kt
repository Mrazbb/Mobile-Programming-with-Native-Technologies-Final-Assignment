package com.example.finalassignment.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalassignment.BuildConfig
import com.example.finalassignment.R
import com.example.finalassignment.model.RemoteImage
import com.example.finalassignment.viewmodel.QuotesHomeViewModel
import com.example.finalassignment.viewmodel.QuotesHomeViewModelFactory
import com.example.finalassignment.viewmodel.QuoteViewModel
import com.example.finalassignment.viewmodel.RatingViewModel
class CustomException(message: String) : Exception(message)

@Composable
fun QuotesHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    quoteViewModel: QuoteViewModel = viewModel(),
    ratingViewModel: RatingViewModel = viewModel(),
    quoteid: Number? = null
) {

    val context = LocalContext.current

    val screenViewModel: QuotesHomeViewModel = viewModel(
        factory = QuotesHomeViewModelFactory(quoteViewModel, ratingViewModel)
    )

    screenViewModel.initialize(quoteid)
    LaunchedEffect(Unit) {
        screenViewModel.initialize(quoteid)
        Log.d("init----", "init")
    }

    var quotes = quoteViewModel.quotes
    Log.d("init----", "${quotes.toString()}")


    // Use the LoadingContent composable to wrap your main content
    LoadingContent(isLoading = screenViewModel.isLoading.value, errorMessage = screenViewModel.errorMessage.value) {

        if (quotes.isEmpty()) {
            Text(
                context.getString(R.string.no_quotes_available),
                modifier = modifier.padding(16.dp)
            )
        }

        val currentQuoteId by screenViewModel.currentQuoteId
        val selectedRating by screenViewModel.selectedRating

        val currentQuote = quotes.find { it.id == currentQuoteId }

        if (currentQuote == null) {
            Text(
                context.getString(R.string.no_quotes_available),
                modifier = modifier.padding(16.dp)
            )
        } else {
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display the image
                RemoteImage(
                    imageUrl = "${BuildConfig.BASE_URL}${currentQuote.image}",
                    modifier = Modifier.fillMaxWidth().height(250.dp).padding(30.dp)
                )

                // Display the quote text
                Text(
                    text = currentQuote.quote,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth()
                )

                // Display the author
                Text(
                    text = "- ${currentQuote.author}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Display rating stars
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= selectedRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = "$i star",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                                .clickable {
                                    screenViewModel.updateRating(i, currentQuote.id)
                                }
                        )
                    }
                }

                // Button to move to the next quote
                Button(
                    onClick = { screenViewModel.moveToNextQuote() },
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Text(context.getString(R.string.next_quote))
                }
            }

        }


    }
}


