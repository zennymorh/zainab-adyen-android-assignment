package com.adyen.android.assignment.api

import com.adyen.android.assignment.api.model.CloseVenues
import com.adyen.android.assignment.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface PlacesService {
    /**
     * Get venue recommendations.
     *
     * See [the docs](https://developer.foursquare.com/reference/places-nearby)
     */
    @Headers(Constants.ACCEPT_HEADER, Constants.AUTHORIZATION_HEADER)
    @GET(Constants.NEARBY_PLACES)
    suspend fun getVenueRecommendations(@Query("ll") location: String):
            Response<CloseVenues>
}
