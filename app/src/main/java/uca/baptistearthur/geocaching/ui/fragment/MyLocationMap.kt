package uca.baptistearthur.geocaching.ui.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.LocationListener
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.ui.overlay.AddMarkerOverlay
import uca.baptistearthur.geocaching.ui.overlay.MarkerOverlay
import uca.baptistearthur.geocaching.ui.overlay.NewRoadtripOverlay
import uca.baptistearthur.geocaching.ui.overlay.RecenterOverlay

class MyLocationMap : Map() {

    private lateinit var spinner: ProgressBar
    private lateinit var  locationManager: LocationManager
    private var isMapCentered = false;

    private val locationListener = LocationListener { location ->
        val geoPoint = GeoPoint(location.latitude, location.longitude)
        if(!isMapCentered){
            map.controller.setCenter(geoPoint)
            spinner.visibility=View.GONE;
            isMapCentered=true;
        }
        map.invalidate()
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    private fun displaySpinner(view: View){
        spinner = view.findViewById(R.id.mapLoading)
        spinner.visibility=View.VISIBLE
        if (ContextCompat.checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    override fun addMapOverlays(mapView: MapView){
        super.addMapOverlays(mapView)
        Log.d("GeoMap", "MyLocationOverlay")

        // Recenter Overlay
        val recenter = RecenterOverlay(GpsMyLocationProvider(context), map)
        recenter.enableMyLocation()
        map.overlays.add(recenter);

        // My Location Overlay
        val myLocation = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        myLocation.enableMyLocation()
        map.overlays.add(myLocation)

        // Add Marker Overlay
        val addMarker = AddMarkerOverlay(OSRMRoadManager(context, userAgent))
        map.overlays.add(addMarker);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("GeoMap", "MAP ON CREATE VIEW")
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        map = view.findViewById(R.id.mapView)
        configureMap()
        displaySpinner(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d("GeoMap", "MAP RESUME")
        if (ContextCompat.checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("GeoMap", "MAP PAUSE")
        if (ContextCompat.checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.removeUpdates(locationListener)
            isMapCentered=false;
        }
    }
}