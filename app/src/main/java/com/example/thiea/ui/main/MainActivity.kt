package com.example.thiea.ui.main

import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.thiea.R
import com.example.thiea.databinding.ActivityMainBinding
import com.example.thiea.ui.util.navermapkey.Companion.NAVER_KEY
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMap.OnMapClickListener
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView : MapView
    private lateinit var naverMap: NaverMap
    private lateinit var userlocation: Location

    private var markerlist = mutableListOf<Marker>()
    private var markerloactionlist = mutableListOf<Location>()


    val infoWindow = InfoWindow()



    private fun snackbartoast(mess: String)
    {
        Snackbar.make(
            binding.root,
            mess,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun pingcreate(inputtext: String) {
        val newmarker: Marker = Marker()
        markerlist.add(newmarker)
        markerloactionlist.add(userlocation)
        //pingspawn(inputtext)


        newmarker.setIconPerspectiveEnabled(true)
        val lat = userlocation.latitude
        val lng = userlocation.longitude // Corrected typo
        newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_1))
        newmarker.setAlpha(0.8f)
        newmarker.height = 50
        newmarker.tag = inputtext
        newmarker.width = 50
        newmarker.setPosition(LatLng(lat, lng))
        newmarker.setMap(naverMap)

        newmarker.setOnClickListener { overlay ->
            // Set the content of the info window
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
                override fun getText(infoWindow: InfoWindow): CharSequence {
                    return inputtext
                }
            }
            // Open the info window above the marker
            infoWindow.open(newmarker)
            true
        }


    }

    private fun pingspawn(inputtext: String) {
        val infoWindow = InfoWindow()

        markerlist.forEachIndexed { index, i ->
            i.setIconPerspectiveEnabled(true)
            val lat = markerloactionlist[index].latitude
            val lng = markerloactionlist[index].longitude // Corrected typo
            i.setIcon(OverlayImage.fromResource(R.drawable.ping_1))
            i.setAlpha(0.8f)
            i.height = 50
            i.tag = inputtext
            i.width = 50
            i.setPosition(LatLng(lat, lng))
            i.setMap(naverMap)

            val markerTag = i.tag as String
            i.setOnClickListener { overlay ->
                // Set the content of the info window
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
                    override fun getText(infoWindow: InfoWindow): CharSequence {
                        return markerTag
                    }
                }
                // Open the info window above the marker
                infoWindow.open(i)
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("${NAVER_KEY}")

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.btAdd.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val confirm = mDialogView.findViewById<Button>(R.id.bt_confirm)
            confirm.setOnClickListener {

                val inputText = mDialogView.findViewById<EditText>(R.id.et_write).text.toString()
                pingcreate(inputText)

                mAlertDialog.dismiss()
            }
        }
        binding.btMy.setOnClickListener {
            snackbartoast("이 모드에선 지원되지 않는 기능입니다.")
        }
        binding.btSearch.setOnClickListener {
            snackbartoast("이 모드에선 지원되지 않는 기능입니다.")
        }

    }

    override fun onMapReady(map: NaverMap) {


        naverMap = map;
        naverMap.mapType = NaverMap.MapType.Navi
        naverMap.isNightModeEnabled = true
        Log.d("test", "asd")
        naverMap.uiSettings.isScaleBarEnabled = false
        naverMap.uiSettings.isCompassEnabled = false
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isLocationButtonEnabled = true

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.locationOverlay.circleRadius = 100
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)

        naverMap.addOnLocationChangeListener {location ->
            userlocation = location
        }


        naverMap.onMapClickListener =
            OnMapClickListener { coord: PointF?, point: LatLng? -> infoWindow.close() }

    }

}


