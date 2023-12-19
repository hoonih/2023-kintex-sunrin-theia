package com.example.thiea.ui.main

import android.content.Intent
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.thiea.R
import com.example.thiea.data.model.Post
import com.example.thiea.data.model.Postreq
import com.example.thiea.data.model.PostsResponse
import com.example.thiea.data.model.reversegeocode
import com.example.thiea.databinding.ActivityMainBinding
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.PingService
import com.example.thiea.remote.service.ReversegeoService
import com.example.thiea.ui.main.dialog.CompleteDialogFragment
import com.example.thiea.ui.main.dialog.EmotionDialogFragment
import com.example.thiea.ui.search_user.SearchActivity
import com.example.thiea.ui.util.navermapkey.Companion.NAVER_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView : MapView
    private lateinit var naverMap: NaverMap
    private var userlocation: Location? = null

    private var markerlist = mutableListOf<Marker>()
    private var markerloactionlist = mutableListOf<Location>()
    private var emotion: Int = 0
    private var posttext: String = ""


    val infoWindow = InfoWindow()


    fun setemotion(emotionint : Int) {
        emotion = emotionint
    }
    fun setposttext(postextstring : String) {
        posttext = postextstring
        postretrofit()
    }
    private fun postretrofit() {                                                     //핑을 찍기 위해 포스트를 한다
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE);
        val userid = sp.getString("userId", null)
        val postservice = RetrofitClient.getRetrofitmain().create(PingService::class.java)


        val post = Postreq(userid.toString(), posttext, userlocation!!.latitude, userlocation!!.longitude, emotion)
        Log.d("theia", "test : $post")

        postservice.pingcreate(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {

                if (response.isSuccessful) {
                    val myResponse = response.body()
                    bottomDialog(CompleteDialogFragment())
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body, ${userid.toString()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }
        })
    }

    private fun getPing(pinglatitude: Double, pinglongtitude: Double) {
        val getping = RetrofitClient.getRetrofitmain().create(PingService::class.java)

        getping.pingsearch(pinglatitude, pinglongtitude).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful) {
                    val myResponse = response.body()?.posts
                    Log.d("theia", "${response.body()?.posts}")
                    myResponse?.forEach {
                        pingcreate(it.text, it.sentiment, it.location.latitude, it.location.longitude)
                    }
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                Log.d("theia", "error - body : $call.")
                Log.d("theia", "API FAIL: ${t.message}")
                Log.d("theia","API FAIL: ${t.cause}")
            }

        })

    }

    fun bottomDialog(fragment: BottomSheetDialogFragment) {
        val bottomSheet = fragment
        bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun snackbartoast(mess: String)
    {
        Snackbar.make(
            binding.root,
            mess,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun pingcreate(inputtext: String, pingemo: Int, latitude: Double, longtitude: Double) {
        val newmarker: Marker = Marker()
        newmarker.setIconPerspectiveEnabled(true)
        when (pingemo) {
            1 -> newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_happy))
            2 -> newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_sad))
            3 -> newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_angry))
            4 -> newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_amaze))
            else -> newmarker.setIcon(OverlayImage.fromResource(R.drawable.ping_tired))
        }
        newmarker.setAlpha(0.8f)
        newmarker.height = 100
        newmarker.tag = inputtext
        newmarker.width = 100
        newmarker.setPosition(LatLng(latitude, longtitude))
        Log.d("theia", "${LatLng(latitude, longtitude)}")
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
            bottomDialog(EmotionDialogFragment())

        }
        binding.btMy.setOnClickListener {
            snackbartoast("이 모드에선 지원되지 않는 기능입니다.")
        }
        binding.btSearch.setOnClickListener {
            var intnet = Intent(this, SearchActivity::class.java)
            startActivity(intnet)
        }
        val timer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                if (userlocation != null) {
                    getPing(userlocation!!.latitude, userlocation!!.longitude)
                    geocording()
                }
            }
        }

        timer.schedule(timerTask, 0, 5000)


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

    fun geocording() {

        val reversgeoservice = RetrofitClient.getRetrofit().create(ReversegeoService::class.java)

        val location = "${userlocation!!.longitude},${userlocation!!.latitude}"

        reversgeoservice.getReversgeo(coords = location).enqueue(object : Callback<reversegeocode> {
            override fun onResponse(
                call: Call<reversegeocode>,
                response: Response<reversegeocode>
            ) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    val area3Name = myResponse?.results?.get(0)?.region?.area3?.name
                    if (area3Name != null) {
                        binding.txRegionname.text = area3Name
                    }
                } else {
                    try { val body = response.errorBody()!!.string()

                        Log.d("theia", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<reversegeocode>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }
        })
    }

}


