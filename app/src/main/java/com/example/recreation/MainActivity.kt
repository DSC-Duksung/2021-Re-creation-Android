package com.example.recreation

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val navigationView = findViewById<BottomNavigationView>(R.id.navigationView)
        navigationView.setOnNavigationItemSelectedListener(this)
        navigationView.selectedItemId = R.id.home_item
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.home_item ->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, HomeFragment())
                transaction.commit()
                return true
            }
            R.id.camera_item ->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, CameraFragment())
                transaction.commit()
                return true
            }
            R.id.search_item ->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, SearchFragment())
                transaction.commit()
                return true
            }
            R.id.profile_item ->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, ProfileFragment())
                transaction.commit()
                return true
            }
        }
        return false
    }
}