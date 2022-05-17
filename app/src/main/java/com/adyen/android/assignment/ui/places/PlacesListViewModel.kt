package com.adyen.android.assignment.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repository.PlacesListRepositoryImpl
import com.adyen.android.assignment.util.CloseVenueState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesListViewModel @Inject constructor(
    private val placesListRepositoryImpl: PlacesListRepositoryImpl
) : ViewModel() {
    private val closeVenueState = MutableStateFlow<CloseVenueState?>(null)

    fun getVenueRecommendations(location: String) {
        viewModelScope.launch {
            placesListRepositoryImpl.getVenueRecommendations(location).collect {
                when {
                    it.isLoading -> {
                        closeVenueState.value = CloseVenueState.Loading
                    }
                    it.data == null -> {
                        closeVenueState.value =
                            CloseVenueState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { data ->
                            closeVenueState.value = CloseVenueState.Success(data = data)
                        }
                    }
                }
            }
        }
    }
}