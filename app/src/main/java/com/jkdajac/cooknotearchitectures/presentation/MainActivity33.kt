package com.jkdajac.cooknotearchitectures.presentation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.hardware.Camera
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.jkdajac.cooknotearchitectures.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity33 : AppCompatActivity(),
    SurfaceHolder.Callback,
    View.OnClickListener,
    Camera.PictureCallback,
    Camera.PreviewCallback, Camera.AutoFocusCallback {
    private  var camera: Camera? = null
    private var preview: SurfaceView? = null
    private var surfaceHolder: SurfaceHolder? = null
    private var shotBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main33)

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // если хотим, чтобы приложение было полноэкранным
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.activity_mains22)
//
        // наше SurfaceView имеет имя SurfaceView01
        preview = findViewById<View>(R.id.SurfaceView01) as SurfaceView
        surfaceHolder = preview!!.holder
//        surfaceHolder.addCallback(this)
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        // кнопка имеет имя Button01
        shotBtn = findViewById<View>(R.id.Button01) as Button
//        shotBtn.setText("Shot")
//        shotBtn.setOnClickListener(this)
    }
    override fun onResume() {
        super.onResume()
        camera = Camera.open()
    }

    override fun onPause() {
        super.onPause()
        if (camera != null) {
            camera!!.setPreviewCallback(null)
            camera!!.stopPreview()
            camera!!.release()
            camera = null
        }
    }
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
//    fun onPreviewFrame(paramArrayOfByte: ByteArray?, paramCamera: Camera?) {
//        // здесь можно обрабатывать изображение, показываемое в preview
//    }
//
    var imageView: ImageView? = null
    var button: Button? = null
    var photoFile: File? = null
    val CAPTURE_IMAGE_REQUEST = 1


    var mCurrentPhotoPath: String? = null
    private val IMAGE_DIRECTORY_NAME = "VLEMONN"
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
//        private fun captureImage2() {
//            try {
//                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                photoFile = createImageFile4()
//                if (photoFile != null) {
//                    displayMessage(baseContext, photoFile!!.absolutePath)
//                    Log.i("Mayank", photoFile!!.absolutePath)
//                    val photoURI: Uri = Uri.fromFile(photoFile)
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST)
//                }
//            } catch (e: Exception) {
//                displayMessage(baseContext, "Camera is not available.$e")
//            }
//        }
//
//        private fun captureImage() {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CAMERA
//                ) !== PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf<String>(
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ),
//                    0
//                )
//            } else {
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                if (takePictureIntent.resolveActivity(packageManager) != null) {
//                    // Create the File where the photo should go
//                    try {
//                        photoFile = createImageFile()
//                        displayMessage(baseContext, photoFile!!.absolutePath)
//                        Log.i("Mayank", photoFile!!.absolutePath)
//
//                        // Continue only if the File was successfully created
//                        if (photoFile != null) {
//                            val photoURI: Uri = FileProvider.getUriForFile(
//                                this,
//                                "com.vlemonn.blog.captureimage.fileprovider",
//                                photoFile!!
//                            )
//                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
//                        }
//                    } catch (ex: Exception) {
//                        // Error occurred while creating the File
//                        displayMessage(baseContext, ex.message.toString())
//                    }
//                } else {
//                    displayMessage(baseContext, "Nullll")
//                }
//            }
//
//        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                camera?.setPreviewDisplay(holder)
                camera?.setPreviewCallback(this)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val previewSize: Camera.Size? = camera?.getParameters()?.getPreviewSize()
            val aspect: Float = previewSize?.width as Float / previewSize.height
            val previewSurfaceWidth = preview!!.width
            val previewSurfaceHeight = preview!!.height
            val lp: ActionBar.LayoutParams = preview!!.layoutParams as ActionBar.LayoutParams

            // здесь корректируем размер отображаемого preview, чтобы не было искажений
            if (this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                // портретный вид
                camera?.setDisplayOrientation(90)
                lp.height = previewSurfaceHeight
                lp.width = (previewSurfaceHeight / aspect).toInt()
            } else {
                // ландшафтный
                camera?.setDisplayOrientation(0)
                lp.width = previewSurfaceWidth
                lp.height = (previewSurfaceWidth / aspect).toInt()
            }
            preview!!.layoutParams = lp
            camera?.startPreview()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            TODO("Not yet implemented")
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            TODO("Not yet implemented")
        }

        override fun onClick(v: View?) {
            if (v === shotBtn) {
            // либо делаем снимок непосредственно здесь
            // 	либо включаем обработчик автофокуса

            //camera.takePicture(null, null, null, this);
            camera?.autoFocus(this)
        }
        }

        override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
            // сохраняем полученные jpg в папке /sdcard/CameraExample/
            // имя файла - System.currentTimeMillis()
            try {
                val saveDir = File("/sdcard/CameraExample/")
                if (!saveDir.exists()) {
                    saveDir.mkdirs()
                }
                val os = FileOutputStream(
                    String.format(
                        "/sdcard/CameraExample/%d.jpg",
                        System.currentTimeMillis()
                    )
                )
                os.write(data)
                os.close()
            } catch (e: Exception) {
            }

            // после того, как снимок сделан, показ превью отключается. необходимо включить его
            camera?.startPreview()
        }

        override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
            TODO("Not yet implemented")
        }

        override fun onAutoFocus(success: Boolean, camera: Camera?) {
            if (success) {
                // если удалось сфокусироваться, делаем снимок
                camera?.takePicture(null, null, null, this)
            }
        }
    }
