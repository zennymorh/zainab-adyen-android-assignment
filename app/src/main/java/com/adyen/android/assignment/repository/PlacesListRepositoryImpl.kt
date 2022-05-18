package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

open class PlacesListRepositoryImpl @Inject constructor(
    private val placesService: PlacesService
) : IPlacesListRepository {
    override suspend fun getVenueRecommendations(location: String): Flow<State<List<Result>>> {
        return flow {

            emit(State.loading())

            val response = placesService.getVenueRecommendations(location)

            try {
                if (!response.isSuccessful) {
                    emit(State.error(message = "Error ${response.code()}"))
                } else {
                    emit(State.data(data = response.body()?.results))
                }
            } catch (e: HttpException) {
                emit(
                    State.error(message = e.code().toString())
                )
            }
        }.flowOn(Dispatchers.IO)
    }

}