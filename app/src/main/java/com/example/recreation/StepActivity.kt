package com.example.recreation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recreation.model.ImageClassifier
import com.example.recreation.model.Keys
import com.google.rpc.context.AttributeContext
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.FileNotFoundException
import java.io.IOException


class StepActivity:AppCompatActivity() {

    var count:Int? = 1

    var paperImage = arrayOf(R.drawable.paper2, R.drawable.paper3, R.drawable.paper4)
    var paperStep = arrayOf("Remove other material that is different from paper such as tape and spring.",
        "Remove foreign substances from the inside of paper packs and paper cups.",
        "If there is no paper pack exclusive collection box, tie the paper packs and separate them from other paper.")


    var petImage = arrayOf(R.drawable.pet2, R.drawable.pet3, R.drawable.pet4)
    var petStep = arrayOf("Empty the contents and remove foreign substances.",
        "Remove other material that is different from plastics such as labels and accessories.",
        "Separate the transparent color and the non-transparent color from each other.")

    var glassImage = arrayOf(R.drawable.glass2,R.drawable.glass3,R.drawable.glass4)
    var glassStep = arrayOf("Empty the contents and remove foreign substances.",
        "A glass bottle capable of receiving a deposit is returned to the retailer and refunded the deposit.",
        "Other than this, be careful not to break it.")

    var modelClass:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val bundle: Bundle? = intent.extras
        modelClass = intent.getStringExtra("modelClass").toString()

        // 첫번쨰 Question
        function1(0)


        next_button.setOnClickListener {
            //back_button.visibility = View.VISIBLE

            count = count?.plus(1)

            when (count) {
                2 -> function1(1)
                3 -> function1(2)
                4 -> {
                    // 물 뿌리는 화면으로 전환
                    val intent = Intent(this, WateringActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        back_button.setOnClickListener {
            count = count?.minus(1)
            when (count) {
                1 -> function1(0)
                2 -> function1(1)
                3 -> function1(2)
                else -> finish()
            }
        }

        btn_back.setOnClickListener {
            finish()
        }


    }

    fun function1(a:Int){
        when (modelClass) {
            "paper" -> {
                qnaText.text = glassStep[a]
                img_preview1.setImageResource(glassImage[a])
            }
            "glass" -> {
                    qnaText.text = paperStep[a]
                    img_preview1.setImageResource(paperImage[a])
            }
            "pet" -> {
                qnaText.text = petStep[a]
                img_preview1.setImageResource(petImage[a])
            }

        }
    }
}








