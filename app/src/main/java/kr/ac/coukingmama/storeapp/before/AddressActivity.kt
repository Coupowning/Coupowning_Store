package kr.ac.coukingmama.storeapp.before

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kr.ac.coukingmama.storeapp.R
import kr.ac.coukingmama.storeapp.databinding.ActivityAddressBinding


class AddressActivity : AppCompatActivity(), OnMapReadyCallback {
    var slatLng : LatLng? = null
    val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    lateinit var binding : ActivityAddressBinding
    private var strings : ArrayList<String>? = null
    lateinit var mapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView = binding.navermap
        mapView.getMapAsync(this)
        if(intent.getStringArrayListExtra("inform") != null){
            strings = intent.getStringArrayListExtra("inform")!!
        }
        binding.back.setOnClickListener{
            finish()
        }
    }
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.33992342806354, 126.73348888791088))
        naverMap.moveCamera(cameraUpdate)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isZoomGesturesEnabled = true
        uiSettings.isScrollGesturesEnabled = true
        uiSettings.isTiltGesturesEnabled = false
        uiSettings.isRotateGesturesEnabled = false

        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false)
        val marker = Marker()
        marker.position = LatLng(37.33992342806354, 126.73348888791088)
        marker.map = naverMap
        marker.icon =  OverlayImage.fromResource(R.drawable.ic_marker)
        marker.width = 100
        marker.height = 100
        naverMap.setOnMapClickListener { pointF, latLng ->
            marker.position = latLng
            slatLng = latLng
        }
        binding.saveButton.setOnClickListener{
            if(slatLng != null){
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("x", slatLng!!.latitude)
                intent.putExtra("y", slatLng!!.longitude)
                intent.putExtra("inform", strings)
                Toast.makeText(this, "위치가 등록되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PERMISSION_GRANTED
            ) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}