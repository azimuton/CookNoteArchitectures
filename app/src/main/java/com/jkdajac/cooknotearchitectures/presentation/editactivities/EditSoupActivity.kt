package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jkdajac.cookingnote.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.SoupFragment
import com.jkdajac.data.storage.AppDatabase
import com.jkdajac.data.storage.entity.Soup
import kotlinx.android.synthetic.main.activity_edit_meat.*
import kotlinx.android.synthetic.main.activity_edit_soup.*

class EditSoupActivity : AppCompatActivity() {

    lateinit var soupDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_soup)
        getMyIntents()

        soupDatabase = AppDatabase.getDatabase(this)

        ivEditEditSoup.setOnClickListener {
            chooseImage()
        }

        floatingSoupSaved.setOnClickListener {

            if (etEditSoupTitle.text.isNotEmpty() && etEditSoupContent.text.isNotEmpty()) {
                val title: String = etEditSoupTitle.text.toString()
                val content: String = etEditSoupContent.text.toString()
                val imageUri = tempImageUri


                val soup =
                    Soup(dbsouptitle = title, dbsoupcontent = content, dbsoupimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                soupDatabase.soupDao().insertSoup(soup)

                val intent = Intent(this, SoupFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditSoup.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditSoup.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        soupDatabase.soupDao().delImage()
        cvEditSoup.visibility = View.GONE
        fabAddGalleryEditSoup.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditSoup.visibility = View.VISIBLE
        fabAddGalleryEditSoup.visibility = View.GONE
    }

    fun chooseImage(){
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.type = "image/*"
        photoPickerIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(photoPickerIntent, Pick_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Pick_image) {
            //Получаем URI изображения, преобразуем его в Bitmap
            //объект и отображаем в элементе ImageView нашего интерфейса:
            ivEditSoup.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }

    }
    fun getMyIntents() {
        cvEditSoup.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                fabAddGalleryEditSoup.visibility = View.GONE
                etEditSoupTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditSoupContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditSoup.visibility = View.VISIBLE
                    ivEditSoup.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditSoup.visibility = View.GONE
                }
            }
        }
    }
}

