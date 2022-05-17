package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.util.State
import kotlinx.coroutines.flow.Flow

interface IPlacesListRepository {
    suspend fun getVenueRecommendations(location: String): Flow<State<List<Result>>>
}