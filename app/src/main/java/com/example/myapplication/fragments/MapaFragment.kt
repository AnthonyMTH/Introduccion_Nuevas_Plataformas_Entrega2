package com.example.myapplication.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : Fragment(R.layout.fragment_mapa), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        checkLocationPermission()  // verifica los permisos al iniciar
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // centro del mapa en Arequipa
        val arequipa = LatLng(-16.409047, -71.537451)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa, 12f))

        // habilita la capa de ubicación del usuario si los permisos están otorgados
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map?.isMyLocationEnabled = true
        } else {
            checkLocationPermission()
        }

        // marcadores para las ubicaciones
        addMarkers()
    }

    private fun addMarkers() {
        val locations = listOf(
            LatLng(-16.398803, -71.536865) to "Catedral de Arequipa",
            LatLng(-16.395878, -71.533785) to "Monasterio de Santa Catalina",
            LatLng(-16.398360, -71.536215) to "Iglesia de la Compañía de Jesús",
            LatLng(-16.401229, -71.535762) to "Casa Tristán del Pozo",
            LatLng(-16.404678, -71.534474) to "Monasterio de La Recoleta",
            LatLng(-16.401897, -71.537491) to "Casa del Moral",
            LatLng(-16.404617, -71.533947) to "Iglesia de San Francisco",
            LatLng(-16.405124, -71.535263) to "Iglesia de Santo Domingo",
            LatLng(-16.422939, -71.524669) to "Molino de Sabandía",
            LatLng(-16.400691, -71.537683) to "Museo Santuarios Andinos"
        )

        for ((location, title) in locations) {
            map?.addMarker(MarkerOptions().position(location).title(title))
        }

        // evento al hacer clic en el marcador, se muestra nombre
        map?.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            map?.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    map?.isMyLocationEnabled = true
                }
            }
        }
    }
}