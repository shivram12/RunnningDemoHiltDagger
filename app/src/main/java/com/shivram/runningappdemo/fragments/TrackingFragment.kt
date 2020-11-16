package com.shivram.runningappdemo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.shivram.runningappdemo.R
import com.shivram.runningappdemo.services.Polyline
import com.shivram.runningappdemo.services.Polylines
import com.shivram.runningappdemo.services.TrackingServices
import com.shivram.runningappdemo.utils.Constans
import com.shivram.runningappdemo.utils.Constans.ACTION_PAUSE_SERVICE
import com.shivram.runningappdemo.utils.Constans.ACTION_START_OR_RESUME_SERVICE
import com.shivram.runningappdemo.utils.Constans.MAP_ZOOM
import com.shivram.runningappdemo.utils.Constans.POLYLINE_COLOR
import com.shivram.runningappdemo.utils.Constans.POLYLINE_WIDTH
import com.shivram.runningappdemo.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private var isTracking = false

    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null

    private var pathPoints = mutableListOf<Polyline>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        btnToggleRun.setOnClickListener {
//            sendCommandToService(Constans.ACTION_START_OR_RESUME_SERVICE)

            toggleRun()
        }

        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        TrackingServices.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingServices.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
    }

    private  fun toggleRun(){
        if(isTracking){
            sendCommandToService(ACTION_PAUSE_SERVICE)
        }else
        {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)


        }
    }

    private fun updateTracking(isTracking:Boolean){
        this.isTracking  = isTracking
        if(!isTracking){
            btnToggleRun.text = "Start"
            btnFinishRun.visibility=  View.VISIBLE
        }else{
            btnToggleRun.text = "Stop"

            btnFinishRun.visibility=  View.GONE

        }
    }

    private fun moveCameraToUser() {
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )   
            )
        }
    }


    private fun addAllPolylines() {
        for(polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }
    private fun addLatestPolyline(){
        if(pathPoints.isNotEmpty()&& pathPoints.last().size>1){

            val preLastLatng = pathPoints.last()[pathPoints.last().size-2]
            val lastLatng = pathPoints.last().last()
            val polylineOptions = PolylineOptions().color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH).add(preLastLatng).add(lastLatng)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingServices::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}