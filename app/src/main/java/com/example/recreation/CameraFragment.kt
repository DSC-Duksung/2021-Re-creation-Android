package com.example.recreation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_camera.*

class CameraFragment : Fragment() {

    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_camera.setOnClickListener {
            activity?.let{
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        button_picture.setOnClickListener {
            activity?.let{
                val intent = Intent(activity, PhotoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
