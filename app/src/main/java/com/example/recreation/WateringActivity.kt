package com.example.recreation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class WateringActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)

        var load:ImageView = findViewById<ImageView>(R.id.imageView1)
        Glide.with(this).load(R.drawable.watering).into(load)

        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}