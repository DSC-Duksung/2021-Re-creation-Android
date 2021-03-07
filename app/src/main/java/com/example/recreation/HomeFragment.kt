package com.example.recreation

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        firebaseAuth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth.currentUser
        if ( currentUser != null ) { // 로그인 했다면
            displayName.setText(currentUser.displayName)
        }

        var animation: AnimationDrawable = imageView.background as AnimationDrawable
        animation.start()
        //imageView.setImageDrawable(resources.getDrawable(R.drawable.heather2))
    }
}