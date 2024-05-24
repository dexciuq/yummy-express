package com.dexciuq.yummy_express.presentation.screen.address

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentAddressBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class AddressFragment : Fragment(), OnMapReadyCallback {

    private val binding by lazy { FragmentAddressBinding.inflate(layoutInflater) }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                    && permissions.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
                getCurrentLocation()
            }

            else -> {
                toast(getString(R.string.for_this_feature_we_need_to_access_your_location))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupToolbar()
        setupFloatingActionButtons()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        moveToStartingPoint()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupFloatingActionButtons() {
        binding.currLocation.setOnClickListener {
            getCurrentLocation()
        }

        binding.fab.setOnClickListener {
            showAddressBottomSheetFragment()
        }
    }

    private fun moveToStartingPoint() {
        val astana = LatLng(51.08935, 71.416)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(astana, 14f)
        map.animateCamera(cameraUpdate)
    }


    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        map.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("Current Location")
                        )
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f),
                            300, null
                        )

                        val address = getAddressFromLocation(location.latitude, location.longitude)
                        Handler(Looper.myLooper()!!).postDelayed(
                            { showAddressBottomSheetFragment(address) }, 300
                        )
                    }
                }
        } else {
            requestLocationPermission()
        }
    }

    private fun getAddressFromLocation(lat: Double, lng: Double): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val streetName = address.thoroughfare ?: ""
            val streetNumber = address.subThoroughfare ?: ""
            return "$streetName, $streetNumber"
        } else {
            "Address not found"
        }
    }

    private fun showAddressBottomSheetFragment(address: String = "") {
        val addressBottomSheetFragment = AddressBottomSheetFragment.newInstance(
            address = address,
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
}