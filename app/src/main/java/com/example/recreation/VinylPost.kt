package com.example.recreation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.post_vinyl.*

class VinylPost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_vinyl)

        btn_back.setOnClickListener {
            finish()
        }
    }
}