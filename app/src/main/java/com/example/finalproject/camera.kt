package com.example.finalproject

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import kotlin.contracts.contract

class camera : AppCompatActivity() {
    lateinit var imgView: ImageView
    lateinit var btnChange: Button
    lateinit var imageUri: Uri
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var latitude: TextView
    lateinit var longitude: TextView
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        imgView.setImageURI(null)
        imgView.setImageURI(imageUri)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        supportActionBar?.hide()
        imgView=findViewById(R.id.image)
        btnChange=findViewById(R.id.Button)
        latitude=findViewById(R.id.latitude)
        longitude=findViewById(R.id.longitude)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        imageUri=createImageUri()!!
        btnChange.setOnClickListener {
           contract.launch(imageUri)
            getLocation()
        }

    }

    private fun createImageUri(): Uri? {
        val image =File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.example.finalproject.fileProvider",image)

    }
    private fun getLocation() {
        //check location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
            return

        }
        //get Latitude and longitude
        val location =fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if(it!=null){
                val textLatitude = "Latitude: "+it.latitude.toString()
                val textLongitude= "Longitude: "+it.longitude.toString()
                latitude.text=textLatitude
                longitude.text=textLongitude
            }
        }

    }
}