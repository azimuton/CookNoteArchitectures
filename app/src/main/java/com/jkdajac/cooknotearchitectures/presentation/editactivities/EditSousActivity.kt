package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.SousFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Sous
import kotlinx.android.synthetic.main.activity_edit_soup.*
import kotlinx.android.synthetic.main.activity_edit_sous.*

class EditSousActivity : AppCompatActivity() {

    lateinit var sousDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sous)
        getMyIntents()

        sousDatabase = AppDatabase.getDatabase(this)

        ivEditEditSous.setOnClickListener {
            chooseImage()
        }

        floatingSousSaved.setOnClickListener {

            if (etEditSousTitle.text.isNotEmpty() && etEditSousContent.text.isNotEmpty()) {
                val title: String = etEditSousTitle.text.toString()
                val content: String = etEditSousContent.text.toString()
                val imageUri = tempImageUri


                val sous =
                    Sous(dbsoustitle = title, dbsouscontent = content, dbsousimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                sousDatabase.sousDao().insertSous(sous)

                val intent = Intent(this, SousFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditSous.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditSous.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        sousDatabase.sousDao().delImage()
        cvEditSoup.visibility = View.GONE
        fabAddGalleryEditSous.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditSous.visibility = View.VISIBLE
        fabAddGalleryEditSous.visibility = View.GONE
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
            ivEditSous.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditSous.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditSous.visibility = View.GONE
                etEditSousTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditSousContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditSous.visibility = View.VISIBLE
                    ivEditSous.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditSous.visibility = View.GONE
                }
            }
        }
    }
}
