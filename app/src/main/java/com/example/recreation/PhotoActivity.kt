package com.example.recreation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.recreation.model.ImageClassifier
import com.example.recreation.model.Keys
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_camera.back_button
import kotlinx.android.synthetic.main.activity_camera.btn_back
import kotlinx.android.synthetic.main.activity_camera.img_preview1
import kotlinx.android.synthetic.main.activity_camera.qnaText
import kotlinx.android.synthetic.main.activity_camera.resultText

class PhotoActivity:AppCompatActivity() {

    private lateinit var classifier: ImageClassifier
    var count:Int? = 0
    private val GET_GALLERY_IMAGE = 200
    private val imageview: ImageView? = null
    var imageUri:Uri? = null
    // var stringUri:String? = null
    var modelClass:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        loadImage()

        next_button.setOnClickListener {
            val nextIntent = Intent(this, StepActivity::class.java)
            //    nextIntent.putExtra("stringUri",stringUri)
            nextIntent.putExtra("modelClass",modelClass)
            startActivity(nextIntent)
        }


        classifier = ImageClassifier(getAssets()) // sy: assets의 tflite 연결
    }

    private fun loadImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, GET_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            //val dataUri: Uri? = data.data
            imageUri = data.data
            //   stringUri = imageUri.toString()
            var bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            bitmap = Bitmap.createScaledBitmap(bitmap, Keys.INPUT_SIZE, Keys.INPUT_SIZE, false)
            img_preview1.setImageBitmap(bitmap)
            //classifier 결과
            classifier.recognizeImage(bitmap).subscribeBy(
                onSuccess = {
                    var string: String = it.toString()
                    resultText.text = string.split(",")[0].replace("[", "").trim()
                    modelClass = string.split(",")[0].replace("[", "").trim() }, onError = {
                    resultText.text = "Error"
                }
            )
        }
    }
}