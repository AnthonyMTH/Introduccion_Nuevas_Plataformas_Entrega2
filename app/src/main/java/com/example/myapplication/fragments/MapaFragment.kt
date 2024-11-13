package com.example.myapplication.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
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

    private val locations = listOf(
        LatLng(-16.398803, -71.536865) to "Catedral de Arequipa",
        LatLng(-16.395878, -71.533785) to "Monasterio de Santa Catalina",
        LatLng(-16.398360, -71.536215) to "Iglesia de la Compañía de Jesús",
        LatLng(-16.401229, -71.535762) to "Casa Tristán del Pozo",
        LatLng(-16.404678, -71.534474) to "Monasterio de La Recoleta",
        LatLng(-16.401897, -71.537491) to "Casa del Moral",
        LatLng(-16.404617, -71.533947) to "Iglesia de San Francisco",
        LatLng(-16.405124, -71.535263) to "Iglesia de Santo Domingo",
        LatLng(-16.456549, -71.499515) to "Molino de Sabandía",
        LatLng(-16.400691, -71.537683) to "Museo Santuarios Andinos"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        checkLocationPermission()  // verifica los permisos al iniciar

        // Configura el Spinner
        val spinner = view.findViewById<Spinner>(R.id.location_spinner)
        val locationNames = locations.map { it.second }  // Extrae solo los nombres de las ubicaciones
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locationNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Listener para centrar el mapa en la ubicación seleccionada
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedLocation = locations[position].first
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15f))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada si no se selecciona ninguna opción
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // centro del mapa en Arequipa
        val arequipa = LatLng(-16.409047, -71.537451)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa, 20f))

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
        // comprueba si el permiso de ubicación (ACCESS_FINE_LOCATION) ha sido otorgado
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // si el permiso ya está otorgado, habilita el boton de ubicación en el mapa
            map?.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // verifica si el código de solicitud corresponde al permiso de ubicación
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // si la solicitud de permisos tiene resultados y el permiso fue concedido
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // vuelve a comprobar si el permiso de ubicación está concedido
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    // habilita el boton de ubicación en el mapa, permitiendo ver la ubicación actual
                    map?.isMyLocationEnabled = true
                }
            }
        }
    }
}