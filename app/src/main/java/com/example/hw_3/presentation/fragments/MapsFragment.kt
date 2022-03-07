package com.example.hw_3.presentation.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.hw_3.databinding.FragmentMapsBinding
import com.example.hw_3.presentation.map.MapService
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("MissingPermission")
class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val mapService by lazy {
        MapService(requireContext())
    }

    @ExperimentalCoroutinesApi
    private val launcherOfPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        setLocationEnabled(it)
        mapService
            .locationFlow()
            .onEach { locationListener?.onLocationChanged(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentMapsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcherOfPermission.launch(ACCESS_FINE_LOCATION)

        binding.mapView.getMapAsync {

            googleMap?.isMyLocationEnabled =
                ContextCompat.checkSelfPermission(
                    view.context,
                    ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            googleMap?.apply {
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }

            googleMap?.setLocationSource(object : LocationSource {
                override fun activate(l: LocationSource.OnLocationChangedListener) {
                    locationListener = l
                }

                override fun deactivate() {
                    locationListener = null
                }
            })

            this.googleMap = googleMap
        }
        binding.mapView.onCreate(savedInstanceState)
    }

    private fun setLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        _binding = null
        super.onDestroyView()
    }
}