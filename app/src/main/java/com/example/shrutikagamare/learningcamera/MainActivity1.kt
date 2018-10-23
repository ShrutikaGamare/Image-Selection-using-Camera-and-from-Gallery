package com.example.shrutikagamare.learningcamera


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import com.example.shrutikagamare.learningcamera.R.id.imageView
import kotlinx.android.synthetic.main.uga_camera.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.data
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


//The Intent itself, a call to start the external Activity, and some code to handle the image data when focus returns to your activity.

class MainActivity1 : AppCompatActivity() {
    lateinit var mCurrentPhotoPath: String
    lateinit var imageArray: String
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_SELECT_IMAGE_IN_ALBUM = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uga_camera)

        imageOption.setOnClickListener {
            //makes visible the selection choice
            cardView.visibility = View.VISIBLE
        }

        //image from camera
        camera.setOnClickListener {
            takePhoto()
        }

        //image from gallery
        gallery.setOnClickListener {
            selectImageInAlbum()
        }

        //cancel cardview
        cancel.setOnClickListener {
            cardView.visibility = View.GONE
        }

    }


    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }


    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }


    fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }



    //Android Camera application encodes the photo in the return Intent delivered to onActivityResult() as a small Bitmap in the extras, under the key "data"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val thumbnail = data?.extras!!.get("data") as Bitmap


            val bytes = ByteArrayOutputStream()

            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

            val destination = File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis().toString() + ".jpg")

            val fo: FileOutputStream
            try {
                destination.createNewFile()
                fo = FileOutputStream(destination)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            fileName.setText(destination.name)
            imageView.setImageBitmap(thumbnail)

        }

        if(requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK)
        {
            Toast.makeText(this,"You're in Gallery Activity",Toast.LENGTH_SHORT).show()


        }
    }

}

























