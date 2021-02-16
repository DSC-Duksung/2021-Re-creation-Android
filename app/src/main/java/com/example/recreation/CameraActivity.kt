package com.example.recreation

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity:AppCompatActivity() {
    companion object{
        val MY_CAMERA_REQUEST_CODE = 7171
    }
    var imageUri: Uri? = null
    var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        //var btn_camera = findViewById(R.id.btn_camera) as ImageView
        /* btn_check.setOnClickListener {
             val intent = Intent(this, CheckActivity::class.java)
             intent.putExtra("url",imageUri.toString())
             startActivity(intent)
             finish()
             //startActivityForResult(intent,0)
         }*/
        btn_back.setOnClickListener {
            // val intent = Intent()
            //intent.putExtra("camera_uri",imageUri.toString())
            //intent.putExtra("img",bitmap)
            // setResult(Activity.RESULT_OK,intent)
            // startActivityForResult(intent,100)
            finish()
        }
        //Event

        Dexter.withContext(this)
            .withPermissions(listOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if(p0!!.areAllPermissionsGranted()){
                        val values = ContentValues()
                        values.put(MediaStore.Images.Media.TITLE,"New Picture")
                        values.put(MediaStore.Images.Media.DESCRIPTION,"From Your Camera")
                        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)!!
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE)
                    }else{
                        Toast.makeText(this@CameraActivity,"You must accept all permission", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }

            }).check()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //var img_preview = findViewById(R.id.img_preview) as ImageView
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_CAMERA_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUri!!)
                    img_preview1.setImageBitmap(bitmap)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}
