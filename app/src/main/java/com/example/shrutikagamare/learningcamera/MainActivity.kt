package com.example.shrutikagamare.learningcamera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.uga_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.example.shrutikagamare.learningcamera.R.id.imageView
import java.io.ByteArrayOutputStream

//The Intent itself, a call to start the external Activity, and some code to handle the image data when focus returns to your activity.

class MainActivity : AppCompatActivity()
{
    var currentPath:String?=null
//    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uga_camera)

        imageOption.setOnClickListener {
            //makes visible the selection choice
            cardView.visibility= View.VISIBLE


            //image from camera
            camera.setOnClickListener {
                takePhoto()
            }

            //image from gallery
            gallery.setOnClickListener {
                //selectImageInAlbum()
            }

            //cancel cardview
            cancel.setOnClickListener {
                cardView.visibility=View.GONE
            }
        }

    }




//    fun selectImageInAlbum() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        if (intent.resolveActivity(packageManager) != null)
//        {
//            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
//        }
//    }



    fun takePhoto()
    {

        val directoryPath = Environment.getExternalStorageDirectory().toString() + "/" +  "temp/"
        val filePath = directoryPath + java.lang.Long.toHexString(System.currentTimeMillis()) + ".jpg"
        val directory = File(directoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
       // this.capturePath = filePath // you will process the image from this path if the capture goes well
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(filePath)))
        intent1.putExtra("file",filePath)

        if (intent1.resolveActivity(packageManager) != null) {
//            var photoFile:File?=null
//            try
//            {
//                photoFile=createImage()
//            }
//            catch (e:IOException)
//            {
//                e.printStackTrace()
//            }
//
//            if(photoFile!=null)
//            {
//                var photoURI=FileProvider.getUriForFile(this,"com.example.shrutikagamare.fileprovider",photoFile)
//                intent1.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
//            }
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val photo = data!!.extras!!.get("data") as Bitmap
           val file = data.getStringExtra("file")
            //val Path = mImageCaptureUri.getPath()
            fileName.setText(file)


        }
    }



    fun createImage(): File? {
        val timestamp=SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName="JPEG"+timestamp+""
        var storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image=File.createTempFile(imageName,".jpg",storageDir)
        currentPath=image.absolutePath
        return image
    }



    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    }


}
