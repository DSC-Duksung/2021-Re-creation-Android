package com.example.recreation

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recreation.model.ImageClassifier
import com.example.recreation.model.Keys
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_camera.btn_back
import kotlinx.android.synthetic.main.activity_camera.img_preview1
import kotlinx.android.synthetic.main.activity_camera.next_button
import kotlinx.android.synthetic.main.activity_camera.resultText
import kotlinx.android.synthetic.main.activity_photo.*


class CameraActivity:AppCompatActivity() {

    private val CHOOSE_IMAGE = 1001
    private lateinit var classifier: ImageClassifier
    private lateinit var bitmap: Bitmap

    companion object{
        val MY_CAMERA_REQUEST_CODE = 7171
    }
    var imageUri: Uri? = null
    // var stringUri:String? = null
    var modelClass:String? = null
    //  var count:Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        btn_back.setOnClickListener {
            finish()
        }

        next_button.setOnClickListener {

            val nextIntent = Intent(this, StepActivity::class.java)
            // nextIntent.putExtra("stringUri",stringUri)
            nextIntent.putExtra("modelClass",modelClass)
            startActivity(nextIntent)
        }


        //Event
        Dexter.withContext(this)
            .withPermissions(
                listOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0!!.areAllPermissionsGranted()) {
                        val values = ContentValues()
                        values.put(MediaStore.Images.Media.TITLE, "New Picture")
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From Your Camera")
                        imageUri = contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                        )!!
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE)
                    } else {
                        Toast.makeText(
                            this@CameraActivity,
                            "You must accept all permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }
            }).check()

        classifier = ImageClassifier(getAssets())
        //sy: assets의 tflite 연결
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_CAMERA_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri!!)
                    bitmap = Bitmap.createScaledBitmap(bitmap, Keys.INPUT_SIZE, Keys.INPUT_SIZE, false)
                    //stringUri = imageUri.toString()
                    img_preview1.setImageBitmap(bitmap)


                    // classifier 결과
                    classifier.recognizeImage(bitmap).subscribeBy(
                        onSuccess = {
                            var string: String = it.toString()
                            resultText.text = string.split(",")[0].replace("[", "").trim()
                            modelClass = string.split(",")[0].replace("[", "").trim()
                        }, onError = {
                            resultText.text = "Error"
                        }
                    )
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}