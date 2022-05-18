package com.adyen.android.assignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.repository.PlacesListRepositoryImpl
import com.adyen.android.assignment.ui.places.PlacesListViewModel
import com.adyen.android.assignment.util.CloseVenueState
import com.adyen.android.assignment.util.State
import com.adyen.android.assignment.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PlacesViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineTestRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryImpl: PlacesListRepositoryImpl

    private lateinit var viewModel: PlacesListViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() =
        testCoroutineDispatcher.runBlockingTest {

            viewModel = PlacesListViewModel(repositoryImpl)

            val location = ""

            val response = State.data("", listOf(fakeDataSource))

            val channel = Channel<State<List<Result>>>()

            val flow = channel.consumeAsFlow()

            Mockito.`when`(repositoryImpl.getVenueRecommendations(location)).thenReturn(flow)

            viewModel.getVenueRecommendations(location)

            val job = launch {
                channel.send(response)
            }

            Assert.assertEquals(
                true,
                viewModel.closeVenueState.value == CloseVenueState.Success(listOf(fakeDataSource))
            )
            job.cancel()
        }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() =
        testCoroutineDispatcher.runBlockingTest {

            viewModel = PlacesListViewModel(repositoryImpl)

            val location = ""

            val response = State.data("", emptyList<Result>())

            val channel = Channel<State<List<Result>>>()

            val flow = channel.consumeAsFlow()

            Mockito.`when`(repositoryImpl.getVenueRecommendations(location)).thenReturn(flow)

            viewModel.getVenueRecommendations(location)

            val job = launch {
                channel.send(response)
            }

            Assert.assertEquals(
                false,
                viewModel.closeVenueState.value == CloseVenueState.Success(listOf(fakeDataSource))
            )
            job.cancel()
        }

    private val fakeDataSource = Result(
        categories = null,
        distance = null,
        geocode = null,
        location = null,
        name = "Barbershop",
        timezone = "Queens"
    )
}