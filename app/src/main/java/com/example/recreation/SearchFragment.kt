package com.example.recreation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_paper.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, PaperPost::class.java)
                startActivity(intent)

            }
        }

        btn_pet.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, PlasticPost::class.java)
                startActivity(intent)

            }
        }

        btn_can.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, CanPost::class.java)
                startActivity(intent)

            }
        }

        btn_glass.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, GlassPost::class.java)
                startActivity(intent)

            }
        }

        btn_vinyl.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, VinylPost::class.java)
                startActivity(intent)

            }
        }

        btn_styro.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, StyrofoamPost::class.java)
                startActivity(intent)

            }
        }
    }
}
