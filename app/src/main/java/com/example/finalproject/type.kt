package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

lateinit var b: ImageView
class type : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type)
        b=findViewById(R.id.hos)
        b.setOnClickListener {
            intent= Intent(this,camera::class.java)
            startActivity(intent)
        }
    }
}