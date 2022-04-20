package com.jkdajac.cooknotearchitectures.presentation.editactivities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jkdajac.cookingnote.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.R.layout
import com.jkdajac.cooknotearchitectures.presentation.fragments.MeatFragment
import com.jkdajac.data.storage.AppDatabase
import com.jkdajac.data.storage.entity.Meat
import kotlinx.android.synthetic.main.activity_edit_meat.*
import kotlinx.android.synthetic.main.activity_edit_soup.*


class EditMeatActivity : AppCompatActivity() {

    lateinit var meatDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_edit_meat)
        getMyIntents()

        meatDatabase = AppDatabase.getDatabase(this)

        ivEditEditMeat.setOnClickListener {
            chooseImage()
        }

        floatingMeatSaved.setOnClickListener {

            if (etEditMeatTitle.text.isNotEmpty() && etEditMeatContent.text.isNotEmpty()) {
                val title: String = etEditMeatTitle.text.toString()
                val content: String = etEditMeatContent.text.toString()
                val imageUri = tempImageUri


                val meat = Meat(dbmeattitle = title, dbmeatcontent = content, dbmeatimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                meatDatabase.meatDao().insertMeat(meat)

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
        photoPickerIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(photoPickerIntent, Pick_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Pick_image) {
            //Получаем URI изображения, преобразуем его в Bitmap
            //объект и отображаем в элементе ImageView нашего интерфейса:
            ivEditMeat.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }

    }
    fun getMyIntents() {
        cvEditMeat.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                fabAddGalleryEditMeat.visibility = View.GONE
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


