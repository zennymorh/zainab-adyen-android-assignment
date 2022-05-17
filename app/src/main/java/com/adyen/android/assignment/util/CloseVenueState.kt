package com.adyen.android.assignment.util

import com.adyen.android.assignment.api.model.Result

sealed class CloseVenueState {
    data class Success(val data: List<Result>?) : CloseVenueState()
    data class Error(val message: String) : CloseVenueState()
    object Loading : CloseVenueState()
}
