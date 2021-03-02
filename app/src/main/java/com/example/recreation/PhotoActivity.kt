package com.example.recreation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recreation.model.ImageClassifier
import com.example.recreation.model.Keys
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_camera.*

class PhotoActivity:AppCompatActivity() {


    private lateinit var classifier: ImageClassifier
    var count:Int? = 0
    private val GET_GALLERY_IMAGE = 200
    private val imageview: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        back_button.visibility = View.INVISIBLE

        btn_back.setOnClickListener {
            finish()
        }

        loadImage()

        yes_button.setOnClickListener {
            back_button.visibility = View.VISIBLE
            resultText.setText("Question")

            count = count?.plus(1)
            when (count) {
                1 -> qnaText.setText("1번: print 1")
                2 -> qnaText.setText("2번: print 2")
                3 -> qnaText.setText("3번: print 3")
                4 ->
                    /* watering can gif 넣을 때 사용.
                    {
                        val intent = Intent(this,SplashActivity::class.java)
                        startActivity(intent)
                    }*/
                    //finish()
                    /*
                {
                    supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment())
                    .commit()
                }*/ {

                    //releaseFragment(HomeFragment.newInstance(),"HomeFragment")
                    // 물 뿌리는 화면으로 전환.
                    val intent = Intent(this, WateringActivity::class.java)
                    //intent.putExtra("KEY",1)
                    startActivity(intent)
                }
            }


        }


        back_button.setOnClickListener {
            count = count?.minus(1)
            when(count){
                2 -> qnaText.setText("2번 : print 2")
                3 -> qnaText.setText("3번 : print 3")
                else -> qnaText.setText("1번 : print 1")
            }
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
            val dataUri: Uri? = data.data
            var bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
            bitmap = Bitmap.createScaledBitmap(bitmap, Keys.INPUT_SIZE, Keys.INPUT_SIZE, false)
            img_preview1.setImageBitmap(bitmap)
            //classifier 결과
            classifier.recognizeImage(bitmap).subscribeBy(
                onSuccess = {
                    resultText.text = it.toString()
                }, onError = {
                    resultText.text = "Error"
                }
            )
        }
    }





}