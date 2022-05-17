package com.adyen.android.assignment.ui.places

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adyen.android.assignment.R
import com.adyen.android.assignment.util.Constants
import com.adyen.android.assignment.util.Permissions

class PlacesListFragment : Fragment(R.layout.places_list_fragment) {

    private val viewModel: PlacesListViewModel by viewModels()
    private lateinit var permission: Permissions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission = Permissions(requireContext(), requireActivity())
        permission.getLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission.getLocation()
            }
        }
    }
}