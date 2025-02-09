package com.example.finalassignment.model

import android.content.Context
import android.util.Log
import com.example.finalassignment.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class UserRating(
    var id: Long,
    val userid: String,
    val quoteid: Int,
    val rating: Int,
)


interface RatingsApi {

    // GET endpoint to retrieve ratings for a given user
    @GET("api/ratings")
    suspend fun getRatings(
        @Query("userid") userId: String = RatingsApi.userid
    ): List<UserRating>

    // POST endpoint to send a new rating
    @POST("api/ratings")
    suspend fun postRating(
        @Body rating: UserRating,
        @Query("userid") userId: String = RatingsApi.userid
    ): UserRating

    companion object {
        private var userRatingsService: RatingsApi? = null

        var userid: String = ""

        // Initializes the userid by using a valid Context.
        fun init(context: Context) {
            userid = getUserId(context)
            Log.d("USERID------", userid)
        }

        fun getInstance(): RatingsApi {
            if (userRatingsService == null) {
                userRatingsService = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RatingsApi::class.java)
            }
            return userRatingsService!!
        }
    }
}
