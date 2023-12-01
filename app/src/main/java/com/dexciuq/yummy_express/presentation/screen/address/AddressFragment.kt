package com.dexciuq.yummy_express.presentation.screen.address

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentAddressBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressFragment : Fragment() {

    private val binding by lazy { FragmentAddressBinding.inflate(layoutInflater) }
    private val mapKit by lazy { MapKitFactory.getInstance() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MapKitFactory.initialize(context)
    }

    override fun onStart() {
        super.onStart()
        mapKit.onStart()
        binding.mapView.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestLocationPermission()
        setupToolbar()
        setupFloatingActionButton()
        setNightModeEnabled()
        moveToStartingPoint()
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupFloatingActionButton() {
        binding.fab.setOnClickListener {
            showAddressBottomSheetFragment()
        }
    }

    private fun moveToStartingPoint() {
        val astana = Point(51.08935, 71.416)
        binding.mapView.mapWindow.map.move(
            CameraPosition(astana, 13f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null
        )
    }

    private fun setNightModeEnabled() {
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.mapView.mapWindow.map.isNightModeEnabled = true
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                binding.mapView.mapWindow.map.isNightModeEnabled = false
            }

            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }

                else -> {
                    // No location access granted.
                    toast(getString(R.string.for_this_feature_we_need_to_access_your_location))
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showAddressBottomSheetFragment() {
        val addressBottomSheetFragment = AddressBottomSheetFragment(
            onFieldIsBlankClick = {
                toast(getString(R.string.all_fields_should_be_filled))
            },
            onReadyButtonClick = {
                findNavController().navigate(
                    AddressFragmentDirections.actionAddressFragmentToCheckoutFragment(it)
                )
            }
        )
        addressBottomSheetFragment.show(parentFragmentManager, addressBottomSheetFragment.tag)
    }

    override fun onStop() {
        binding.mapView.onStop()
        mapKit.onStop()
        super.onStop()
    }
}