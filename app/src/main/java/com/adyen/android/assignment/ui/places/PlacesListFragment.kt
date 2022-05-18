package com.adyen.android.assignment.ui.places

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.databinding.PlacesListFragmentBinding
import com.adyen.android.assignment.util.CloseVenueState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class PlacesListFragment : Fragment() {
    private var _binding: PlacesListFragmentBinding? = null
    private val binding get() = _binding!!
    private val locationStringArgs: PlacesListFragmentArgs by navArgs()

    private val viewModel by activityViewModels<PlacesListViewModel>()

    private val placeListAdapter: PlacesListAdapter by lazy {
        PlacesListAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlacesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placesListRecycler.adapter = placeListAdapter
        viewModel.getVenueRecommendations(locationStringArgs.location)
        observeViewModel(view)
    }

    private fun observeViewModel(view: View) {
        lifecycleScope.launch {
            viewModel.closeVenueState.collect {
                when (it) {
                    is CloseVenueState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is CloseVenueState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        val snackBar = Snackbar.make(
                            view,
                            "Error, please try again",
                            Snackbar.LENGTH_INDEFINITE
                        )
                        snackBar.setAction("Retry") {
                            viewModel.getVenueRecommendations(
                                locationStringArgs.location
                            )
                        }
                        snackBar.show()
                    }
                    is CloseVenueState.Success -> {
                        it.data?.let { result -> renderList(result) }
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {}
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderList(result: List<Result>) {
        placeListAdapter.updateList(result)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}