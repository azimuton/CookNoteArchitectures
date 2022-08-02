package com.jkdajac.cooknotearchitectures.presentation.editactivities


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.R.layout
import com.jkdajac.cooknotearchitectures.presentation.fragments.MeatFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Meat
import kotlinx.android.synthetic.main.activity_edit_meat.*
import java.io.File


class EditMeatActivity : AppCompatActivity() {

    lateinit var meatDatabase: AppDatabase
    private val Pick_image = 1
    private val Pick_image2 = 2
    var tempImageUri = "empty"
    //var mCurrentPhotoPath: String? = null
    private lateinit var file : File
    lateinit var mUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_edit_meat)
        getMyIntents()


        meatDatabase = AppDatabase.getDatabase(this)

        ivEditEditMeat.setOnClickListener {
            chooseImage()
        }

//        ivCameraEditMeat.setOnClickListener {
//            mUri = generateFileUri();
//            if (mUri == null) {
//                Toast.makeText(this, "SD card not available", Toast.LENGTH_SHORT).show()
//            }
//            chooseImage2()
////            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
////            startActivityForResult(intent,Pick_image2)
//        }

        floatingMeatSaved.setOnClickListener {

            if (etEditMeatTitle.text.isNotEmpty() && etEditMeatContent.text.isNotEmpty()) {
                val title: String = etEditMeatTitle.text.toString()
                val content: String = etEditMeatContent.text.toString()
                val imageUri = tempImageUri
                val meat = Meat(dbmeattitle = title, dbmeatcontent = content, dbmeatimage = imageUri)
                meatDatabase.meatDao().insertMeat(meat)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MeatFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(
                    this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG).show()
            }
        }
        fabAddGalleryEditMeat.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditMeat.setOnClickListener {
            clickDeleteImage()
        }
    }

    private fun clickDeleteImage() {
        meatDatabase.meatDao().delImage()
        cvEditMeat.visibility = View.GONE
        fabAddGalleryEditMeat.visibility = View.VISIBLE
    }

    private fun clickFabAddImage() {
        cvEditMeat.visibility = View.VISIBLE
        fabAddGalleryEditMeat.visibility = View.GONE
    }

    fun chooseImage(){
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, Pick_image)
    }

    private fun chooseImage2(){
        //val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //photoPickerIntent.type = "CameraTest/*"
        //startActivityForResult(photoPickerIntent, Pick_image2)
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,Pick_image2)
        intent.type = "CameraTest/*"
    }

    private fun generateFileUri(): String {
//        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
//            return null
        val path = File(Environment.getExternalStorageDirectory(), "CameraTest")
//        if (!path.exists()) {
//            if (!path.mkdirs()) {
//                return null
//            }
//        }
        val timeStamp = System.currentTimeMillis().toString()
        val newFile = File(path.path + File.separator + timeStamp + ".jpg")
        return Uri.fromFile(newFile).toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Pick_image && data != null) {
            //Получаем URI изображения, преобразуем его в Bitmap
            //объект и отображаем в элементе ImageView нашего интерфейса:
            ivEditMeat.setImageURI(data.data)
            tempImageUri = data.data.toString()
            contentResolver.takePersistableUriPermission(data.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else
        if (resultCode == RESULT_OK && requestCode == Pick_image2){
            val takenImage = data?.extras?.get("data") as Bitmap
            ivEditMeat.setImageBitmap(takenImage)
        }
    }
    fun getMyIntents() {
        cvEditMeat.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditMeat.visibility = View.GONE
                etEditMeatTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditMeatContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditMeat.visibility = View.VISIBLE
                        ivEditMeat.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                    } else {
                    cvEditMeat.visibility = View.GONE
                    }
            }
        }
    }
}


