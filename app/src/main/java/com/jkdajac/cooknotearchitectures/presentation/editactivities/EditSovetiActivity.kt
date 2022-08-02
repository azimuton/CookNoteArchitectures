package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.SovetiFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Soveti
import kotlinx.android.synthetic.main.activity_edit_soveti.*

class EditSovetiActivity : AppCompatActivity() {

    lateinit var sovetiDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_soveti)
        getMyIntents()

        sovetiDatabase = AppDatabase.getDatabase(this)

        ivEditEditSoveti.setOnClickListener {
            chooseImage()
        }

        floatingSovetiSaved.setOnClickListener {

            if (etEditSovetiTitle.text.isNotEmpty() && etEditSovetiContent.text.isNotEmpty()) {
                val title: String = etEditSovetiTitle.text.toString()
                val content: String = etEditSovetiContent.text.toString()
                val imageUri = tempImageUri


                val soveti =
                    Soveti(dbsovetititle = title, dbsoveticontent = content, dbsovetiimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                sovetiDatabase.sovetiDao().insertSoveti(soveti)

                val intent = Intent(this, SovetiFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditSoveti.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditSoveti.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        sovetiDatabase.sovetiDao().delImage()
        cvEditSoveti.visibility = View.GONE
        fabAddGalleryEditSoveti.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditSoveti.visibility = View.VISIBLE
        fabAddGalleryEditSoveti.visibility = View.GONE
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
            ivEditSoveti.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditSoveti.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditSoveti.visibility = View.GONE
                etEditSovetiTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditSovetiContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditSoveti.visibility = View.VISIBLE
                    ivEditSoveti.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditSoveti.visibility = View.GONE
                }
            }
        }
    }
}