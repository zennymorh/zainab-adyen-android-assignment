package com.adyen.android.assignment.util

import com.adyen.android.assignment.BuildConfig

object Constants {
    const val ACCEPT = "Accept: application/json"
    const val AUTHORIZATION = "Authorization: ${BuildConfig.API_KEY}"
    const val BASE_URL = "https://api.foursquare.com/v3/places/"
    const val PERMISSION_ID = 25
}