import android.R
import android.app.ActionBar
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Camera
import android.os.Bundle
import android.util.Size
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.AsynchronousFileChannel.open


//package com.jkdajac.cooknotearchitectures
//
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Environment
//import android.provider.MediaStore
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.FileProvider
//import kotlinx.android.synthetic.main.activity_mains22.*
//import java.io.File
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.jar.Manifest
//
//class MainsActivity22 : AppCompatActivity(), SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback {
//    private var camera: Camera? = null
//    private var surfaceHolder: SurfaceHolder? = null
//    private var preview: SurfaceView? = null
//    private var shotBtn: Button? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // если хотим, чтобы приложение постоянно имело портретную ориентацию
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//
//        // если хотим, чтобы приложение было полноэкранным
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//
//        // и без заголовка
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.activity_mains22)
//
//        // наше SurfaceView имеет имя SurfaceView01
//        preview = findViewById<View>(R.id.SurfaceView01) as SurfaceView
//        surfaceHolder = preview!!.holder
//        surfaceHolder.addCallback(this)
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
//
//        // кнопка имеет имя Button01
//        shotBtn = findViewById<View>(R.id.Button01) as Button
//        shotBtn.setText("Shot")
//        shotBtn.setOnClickListener(this)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        camera = Camera.open()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (camera != null) {
//            camera.setPreviewCallback(null)
//            camera.stopPreview()
//            camera.release()
//            camera = null
//        }
//    }
//
//    fun onClick(v: View) {
//        if (v === shotBtn) {
//            // либо делаем снимок непосредственно здесь
//            // 	либо включаем обработчик автофокуса
//
//            //camera.takePicture(null, null, null, this);
//            camera.autoFocus(this)
//        }
//    }
//
//    fun onPictureTaken(paramArrayOfByte: ByteArray?, paramCamera: Camera) {
//        // сохраняем полученные jpg в папке /sdcard/CameraExample/
//        // имя файла - System.currentTimeMillis()
//        try {
//            val saveDir = File("/sdcard/CameraExample/")
//            if (!saveDir.exists()) {
//                saveDir.mkdirs()
//            }
//            val os = FileOutputStream(
//                String.format(
//                    "/sdcard/CameraExample/%d.jpg",
//                    System.currentTimeMillis()
//                )
//            )
//            os.write(paramArrayOfByte)
//            os.close()
//        } catch (e: Exception) {
//        }
//
//        // после того, как снимок сделан, показ превью отключается. необходимо включить его
//        paramCamera.startPreview()
//    }
//
//    fun onAutoFocus(paramBoolean: Boolean, paramCamera: Camera) {
//        if (paramBoolean) {
//            // если удалось сфокусироваться, делаем снимок
//            paramCamera.takePicture(null, null, null, this)
//        }
//    }
//
//    fun onPreviewFrame(paramArrayOfByte: ByteArray?, paramCamera: Camera?) {
//        // здесь можно обрабатывать изображение, показываемое в preview
//    }
//
//    override fun surfaceCreated(holder: SurfaceHolder) {
//        try {
//            camera.setPreviewDisplay(holder)
//            camera.setPreviewCallback(this)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        val previewSize: Size = camera.getParameters().getPreviewSize()
//        val aspect: Float = previewSize.width as Float / previewSize.height
//        val previewSurfaceWidth = preview!!.width
//        val previewSurfaceHeight = preview!!.height
//        val lp: ActionBar.LayoutParams = preview!!.layoutParams as ActionBar.LayoutParams
//
//        // здесь корректируем размер отображаемого preview, чтобы не было искажений
//        if (this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
//            // портретный вид
//            camera.setDisplayOrientation(90)
//            lp.height = previewSurfaceHeight
//            lp.width = (previewSurfaceHeight / aspect).toInt()
//        } else {
//            // ландшафтный
//            camera.setDisplayOrientation(0)
//            lp.width = previewSurfaceWidth
//            lp.height = (previewSurfaceWidth / aspect).toInt()
//        }
//        preview!!.layoutParams = lp
//        camera.startPreview()
//    }
//
//    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun surfaceDestroyed(holder: SurfaceHolder) {
//        TODO("Not yet implemented")
//    }
//    var imageView: ImageView? = null
//    var button: Button? = null
//    var photoFile: File? = null
//    val CAPTURE_IMAGE_REQUEST = 1
//
//
//    var mCurrentPhotoPath: String? = null
//    private val IMAGE_DIRECTORY_NAME = "VLEMONN"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_mains22)
//        imageView = findViewById(R.id.imageView)
//        //button = findViewById(R.id.btnCaptureImage)
//        btnCaptureImage.setOnClickListener(View.OnClickListener {
//            captureImage()
//        })
//    }
//
//    /* Capture Image function for 4.4.4 and lower. Not tested for Android Version 3 and 2 */
//    private fun captureImage2() {
//        try {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            photoFile = createImageFile4()
//            if (photoFile != null) {
//                displayMessage(baseContext, photoFile!!.absolutePath)
//                Log.i("Mayank", photoFile!!.absolutePath)
//                val photoURI: Uri = Uri.fromFile(photoFile)
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST)
//            }
//        } catch (e: Exception) {
//            displayMessage(baseContext, "Camera is not available.$e")
//        }
//    }
//
//    private fun captureImage() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) !== PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                0
//            )
//        } else {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(packageManager) != null) {
//                // Create the File where the photo should go
//                try {
//                    photoFile = createImageFile()
//                    displayMessage(baseContext, photoFile!!.absolutePath)
//                    Log.i("Mayank", photoFile!!.absolutePath)
//
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        val photoURI: Uri = FileProvider.getUriForFile(
//                            this,
//                            "com.vlemonn.blog.captureimage.fileprovider",
//                            photoFile!!
//                        )
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
//                    }
//                } catch (ex: Exception) {
//                    // Error occurred while creating the File
//                    displayMessage(baseContext, ex.message.toString())
//                }
//            } else {
//                displayMessage(baseContext, "Nullll")
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        //Bundle extras = data.getExtras();
//        //Bitmap imageBitmap = (Bitmap) extras.get("data");
//        //imageView.setImageBitmap(imageBitmap);
//        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            val myBitmap: Bitmap = BitmapFactory.decodeFile(photoFile!!.absolutePath)
//            imageView!!.setImageBitmap(myBitmap)
//        } else {
//            displayMessage(baseContext, "Request cancelled or something went wrong.")
//        }
//    }
//
//    private fun createImageFile4(): File? {
//        // External sdcard location
//        val mediaStorageDir = File(
//            Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//            IMAGE_DIRECTORY_NAME
//        )
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                displayMessage(baseContext, "Unable to create directory.")
//                return null
//            }
//        }
//        val timeStamp: String = SimpleDateFormat(
//            "yyyyMMdd_HHmmss",
//            Locale.getDefault()
//        ).format(Date())
//        return File(
//            mediaStorageDir.path + File.separator
//                .toString() + "IMG_" + timeStamp + ".jpg"
//        )
//    }
//
//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val imageFileName = "JPEG_" + timeStamp + "_"
//        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(
//            imageFileName,  /* prefix */
//            ".jpg",  /* suffix */
//            storageDir /* directory */
//        )
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.absolutePath
//        return image
//    }
//
//    private fun displayMessage(context: Context, message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//    }
//
////    fun onRequestPermissionsResult(
////        requestCode: Int,
////        permissions: Array<String?>?,
////        grantResults: IntArray
////    ) {
////        if (permissions != null) {
////            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        }
////        if (requestCode == 0) {
////            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
////                captureImage()
////            }
////        }
////    }
//}