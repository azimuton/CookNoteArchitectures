package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.SoleniaFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Solenia
import kotlinx.android.synthetic.main.activity_edit_solenia.*

class EditSoleniaActivity: AppCompatActivity() {

    lateinit var soleniaDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_solenia)
        getMyIntents()

        soleniaDatabase = AppDatabase.getDatabase(this)

        ivEditEditSolenia.setOnClickListener {
            chooseImage()
        }

        floatingSoleniaSaved.setOnClickListener {

            if (etEditSoleniaTitle.text.isNotEmpty() && etEditSoleniaContent.text.isNotEmpty()) {
                val title: String = etEditSoleniaTitle.text.toString()
                val content: String = etEditSoleniaContent.text.toString()
                val imageUri = tempImageUri


                val solenia =
                    Solenia(dbsoleniatitle = title, dbsoleniacontent = content, dbsoleniaimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                soleniaDatabase.soleniaDao().insertSolenia(solenia)

                val intent = Intent(this, SoleniaFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditSolenia.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditSolenia.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        soleniaDatabase.soleniaDao().delImage()
        cvEditSolenia.visibility = View.GONE
        fabAddGalleryEditSolenia.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditSolenia.visibility = View.VISIBLE
        fabAddGalleryEditSolenia.visibility = View.GONE
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
            ivEditSolenia.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditSolenia.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditSolenia.visibility = View.GONE
                etEditSoleniaTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditSoleniaContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditSolenia.visibility = View.VISIBLE
                    ivEditSolenia.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditSolenia.visibility = View.GONE
                }
            }
        }
    }
}