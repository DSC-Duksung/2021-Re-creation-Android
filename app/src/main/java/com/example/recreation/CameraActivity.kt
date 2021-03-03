package com.example.recreation

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
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

class CameraActivity:AppCompatActivity() {

    private val CHOOSE_IMAGE = 1001
    private lateinit var classifier: ImageClassifier
    private lateinit var bitmap: Bitmap

    companion object{
        val MY_CAMERA_REQUEST_CODE = 7171
    }
    var imageUri: Uri? = null
    var count:Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        back_button.visibility = View.INVISIBLE

        btn_back.setOnClickListener {
            finish()
        }


        yes_button.setOnClickListener {
            back_button.visibility = View.VISIBLE

            count = count?.plus(1)
            when (count) {
                1 -> qnaText.setText("1번: print 1")
                2 -> qnaText.setText("2번: print 2")
                3 -> qnaText.setText("3번: print 3")
                4 -> {
                    // 물 뿌리는 화면으로 전환
                    val intent = Intent(this,WateringActivity::class.java)
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

        classifier = ImageClassifier(getAssets()) // sy: assets의 tflite 연결
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_CAMERA_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUri!!)
                    bitmap = Bitmap.createScaledBitmap(bitmap, Keys.INPUT_SIZE, Keys.INPUT_SIZE, false)
                    img_preview1.setImageBitmap(bitmap)

                    // classifier 결과
                    classifier.recognizeImage(bitmap).subscribeBy(
                        onSuccess = {
                            resultText.text = it.toString().first().toString()
                        }, onError = {
                            resultText.text = "Error"
                        }
                    )
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}