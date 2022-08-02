package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.NapitkiFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Napitki
import kotlinx.android.synthetic.main.activity_edit_napitki.*

class EditNapitkiActivity : AppCompatActivity() {

    lateinit var napitkiDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_napitki)
        getMyIntents()

        napitkiDatabase = AppDatabase.getDatabase(this)

        ivEditEditNapitki.setOnClickListener {
            chooseImage()
        }

        floatingNapitkiSaved.setOnClickListener {

            if (etEditNapitkiTitle.text.isNotEmpty() && etEditNapitkiContent.text.isNotEmpty()) {
                val title: String = etEditNapitkiTitle.text.toString()
                val content: String = etEditNapitkiContent.text.toString()
                val imageUri = tempImageUri


                val napitki =
                    Napitki(dbnapitkititle = title, dbnapitkicontent = content, dbnapitkiimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                napitkiDatabase.napitkiDao().insertNapitki(napitki)

                val intent = Intent(this, NapitkiFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditNapitki.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditNapitki.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        napitkiDatabase.napitkiDao().delImage()
        cvEditNapitki.visibility = View.GONE
        fabAddGalleryEditNapitki.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditNapitki.visibility = View.VISIBLE
        fabAddGalleryEditNapitki.visibility = View.GONE
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
            ivEditNapitki.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditNapitki.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditNapitki.visibility = View.GONE
                etEditNapitkiTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditNapitkiContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditNapitki.visibility = View.VISIBLE
                    ivEditNapitki.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditNapitki.visibility = View.GONE
                }
            }
        }
    }
}