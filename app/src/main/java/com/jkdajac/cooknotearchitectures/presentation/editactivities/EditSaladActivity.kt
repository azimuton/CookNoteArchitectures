package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.SaladFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Salad
import kotlinx.android.synthetic.main.activity_edit_salad.*

class EditSaladActivity : AppCompatActivity() {

    lateinit var saladDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_salad)
        getMyIntents()

        saladDatabase = AppDatabase.getDatabase(this)

        ivEditEditSalad.setOnClickListener {
            chooseImage()
        }

        floatingSaladSaved.setOnClickListener {

            if (etEditSaladTitle.text.isNotEmpty() && etEditSaladContent.text.isNotEmpty()) {
                val title: String = etEditSaladTitle.text.toString()
                val content: String = etEditSaladContent.text.toString()
                val imageUri = tempImageUri


                val salad =
                    Salad(dbsaladtitle = title, dbsaladcontent = content, dbsaladimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                saladDatabase.saladDao().insertSalad(salad)

                val intent = Intent(this, SaladFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditSalad.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditSalad.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        saladDatabase.saladDao().delImage()
        cvEditSalad.visibility = View.GONE
        fabAddGalleryEditSalad.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditSalad.visibility = View.VISIBLE
        fabAddGalleryEditSalad.visibility = View.GONE
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
            ivEditSalad.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditSalad.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditSalad.visibility = View.GONE
                etEditSaladTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditSaladContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditSalad.visibility = View.VISIBLE
                    ivEditSalad.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditSalad.visibility = View.GONE
                }
            }
        }
    }
}
