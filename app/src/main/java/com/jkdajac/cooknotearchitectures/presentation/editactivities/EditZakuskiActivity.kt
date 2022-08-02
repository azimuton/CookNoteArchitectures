package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.ZakuskiFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Zakuski
import kotlinx.android.synthetic.main.activity_edit_zakuski.*

class EditZakuskiActivity : AppCompatActivity() {

    lateinit var zakuskiDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_zakuski)
        getMyIntents()

        zakuskiDatabase = AppDatabase.getDatabase(this)

        ivEditEditZakuski.setOnClickListener {
            chooseImage()
        }

        floatingZakuskiSaved.setOnClickListener {

            if (etEditZakuskiTitle.text.isNotEmpty() && etEditZakuskiContent.text.isNotEmpty()) {
                val title: String = etEditZakuskiTitle.text.toString()
                val content: String = etEditZakuskiContent.text.toString()
                val imageUri = tempImageUri


                val zakuski =
                   Zakuski(dbzakuskititle = title, dbzakuskicontent = content, dbzakuskiimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                zakuskiDatabase.zakuskiDao().insertZakuski(zakuski)

                val intent = Intent(this, ZakuskiFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG).show()
            }
        }
        fabAddGalleryEditZakuski.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditZakuski.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        zakuskiDatabase.zakuskiDao().delImage()
        cvEditZakuski.visibility = View.GONE
        fabAddGalleryEditZakuski.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditZakuski.visibility = View.VISIBLE
        fabAddGalleryEditZakuski.visibility = View.GONE
    }

    fun chooseImage(){
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.type = "image/*"
        //photoPickerIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(photoPickerIntent, Pick_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Pick_image) {
            //Получаем URI изображения, преобразуем его в Bitmap
            //объект и отображаем в элементе ImageView нашего интерфейса:
            ivEditZakuski.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditZakuski.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditZakuski.visibility = View.GONE
                etEditZakuskiTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditZakuskiContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditZakuski.visibility = View.VISIBLE
                    ivEditZakuski.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditZakuski.visibility = View.GONE
                }
            }
        }
    }
}
