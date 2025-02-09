package com.example.finalassignment.model

import com.example.finalassignment.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class GetQuote(
    var id: Int,
    var quote: String,
    var author: String,
    var image: String? = null,
    var rating: Double?,
    var number_of_ratings: Int?,
    var dtcreated: String? = null,
    var dtupdated: String? = null,
    var dtremoved: String? = null
)

interface QuotesApi {

    @GET("api/quote")
    suspend fun getQuotes(): List<GetQuote>

    companion object {
        private var quotesService: QuotesApi? = null

        fun getInstance(): QuotesApi {
            if (quotesService == null) {
                quotesService = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(QuotesApi::class.java)
            }
            return quotesService!!
        }
    }
}
