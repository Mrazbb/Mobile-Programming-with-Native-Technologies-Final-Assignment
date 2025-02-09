package com.example.finalassignment.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalassignment.model.GetQuote
import com.example.finalassignment.model.QuoteReview
import com.example.finalassignment.viewmodel.QuoteViewModel



@Composable
fun QuotesListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    reviewedQuotes: List<QuoteReview>,
    viewedQuotes: List<Number>,
) {

    var quoteViewModel: QuoteViewModel = viewModel()
    var getQuotes = quoteViewModel.getQuotes;
    val sortedQuotes = getQuotes.sortedByDescending { it.number_of_ratings }


    LazyColumn(modifier = modifier) {
        items(getQuotes) { quote ->
            Column(modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = {
                        navController.navigate("home/${quote.id}")
                    })
            ) {
                // quote
                Text(
                    text = quote.quote,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                // author
                Text(
                    text = quote.author,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )

                // rating
                Row ( modifier = Modifier) {
                    if (quote.number_of_ratings != 0) {
                        Text(text = String.format("%.1f",quote.rating), color = Color(0xFF808080))

                        Spacer( modifier = Modifier.padding(end=8.dp))

                        quote.rating?.let { StarRating(rating = it.toInt()) }
                        Spacer( modifier = Modifier.padding(end=8.dp))
                        if (quote.number_of_ratings != 0)
                            Text(text = "(${quote.number_of_ratings})", modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                    }

                }
            }
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
        }
    }
}

@Composable
fun StarRating(rating: Int, modifier: Modifier = Modifier) {
    Log.d("star", "$rating")
    Row(modifier = modifier) {

        // Draw 5 stars. Fill a star if its position is less than or equal to the rating.
        for (i in 1..5) {
            Icon(
                contentDescription = if (i <= rating) "Filled Star" else "Empty Star",
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                tint = if (i <= rating) Color(0xFFFFD700) else Color(0xFF808080), // Gold color for stars
                modifier = Modifier.padding(end = 2.dp)
            )
        }
    }
}
