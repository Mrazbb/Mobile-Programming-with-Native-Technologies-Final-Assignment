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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalassignment.BuildConfig
import com.example.finalassignment.model.RemoteImage
import com.example.finalassignment.model.GetQuote
import com.example.finalassignment.model.QuoteReview
import com.example.finalassignment.model.RatingsApi
import com.example.finalassignment.model.UserRating
import com.example.finalassignment.viewmodel.QuoteViewModel
import com.example.finalassignment.viewmodel.RatingViewModel


@Composable
fun QuotesHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    reviewedQuotes: List<QuoteReview>,
    viewedQuotes: List<Number>,
    quoteViewModel: QuoteViewModel = viewModel(),
    ratingViewModel: RatingViewModel = viewModel(),
    quoteid: Number? = null
) {

    var getQuotes = quoteViewModel.getQuotes

    val userRatings = ratingViewModel.ratingsList


    Log.d("quotes", getQuotes.toString())

    if (getQuotes.isEmpty()) {
        Text(
            text = "No quotes available",
            modifier = modifier.padding(16.dp)
        )
        return
    }

    val initialQuoteId = quoteid ?: getQuotes[0].id
    var currentQuoteId by remember { mutableStateOf(initialQuoteId) }
    var selectedRating by remember { mutableStateOf(0) }

    val currentQuote = getQuotes.find { it.id == currentQuoteId }

    val currentRating = userRatings.find { it.quoteid == currentQuote?.id }

    if (currentQuote == null) {
        Text("Quote not found", modifier = modifier.padding(16.dp))
        return
    }

    if (currentRating != null ) {
        selectedRating = currentRating.rating
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image
        RemoteImage(
            imageUrl = "${BuildConfig.BASE_URL}${currentQuote?.image}",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(30.dp)
        )

        // Quote
        Text(
            text = currentQuote.quote,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )

        // Author
        Text(
            text = "- ${currentQuote.author}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.padding(top = 8.dp)) {
            for(i in 1..5) {
                Icon(
                    imageVector = if (i <= selectedRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "$i star",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                        .clickable {

                            // send review
                            selectedRating = i
                            val newRating = UserRating(
                                id = 0, // Use 0 or an appropriate value if your backend auto-generates the ID.
                                userid = RatingsApi.userid, // Ensure the user ID is initialized
                                quoteid = currentQuote.id,  // Assuming your GetQuote model contains an "id" field
                                rating = selectedRating
                            )
                            ratingViewModel.submitRating(newRating)
                        }
                )
            }
        }

        // Option: Button to move to the next quote
        Button(
            onClick = {


                val currentIndex = getQuotes.indexOfFirst { it.id == currentQuoteId }

                if (currentIndex != -1) {
                    Log.d("current","${currentQuoteId}")
                    val nextIndex = if (currentIndex < getQuotes.lastIndex) currentIndex + 1 else 0

                    currentQuoteId = getQuotes[nextIndex].id
                    Log.d("next","${currentQuoteId}")
                }
                selectedRating = 0
            },
            modifier = Modifier
                .padding(top = 40.dp)
        ) {
            Text("Next Quote")
        }

        RatingViewModel()

    }


}

