package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.DesertiFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Deserti
import kotlinx.android.synthetic.main.activity_edit_deserti.*

class EditDesertiActivity : AppCompatActivity() {

    lateinit var desertiDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_deserti)
        getMyIntents()

        desertiDatabase = AppDatabase.getDatabase(this)

        ivEditEditDeserti.setOnClickListener {
            chooseImage()
        }

        floatingDesertiSaved.setOnClickListener {

            if (etEditDesertiTitle.text.isNotEmpty() && etEditDesertiContent.text.isNotEmpty()) {
                val title: String = etEditDesertiTitle.text.toString()
                val content: String = etEditDesertiContent.text.toString()
                val imageUri = tempImageUri


                val deserti =
                    Deserti(dbdesertititle = title, dbdeserticontent = content, dbdesertiimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                desertiDatabase.desertiDao().insertDeserti(deserti)

                val intent = Intent(this, DesertiFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditDeserti.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditDeserti.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        desertiDatabase.desertiDao().delImage()
        cvEditDeserti.visibility = View.GONE
        fabAddGalleryEditDeserti.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditDeserti.visibility = View.VISIBLE
        fabAddGalleryEditDeserti.visibility = View.GONE
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
            ivEditDeserti.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditDeserti.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditDeserti.visibility = View.GONE
                etEditDesertiTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditDesertiContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditDeserti.visibility = View.VISIBLE
                    ivEditDeserti.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditDeserti.visibility = View.GONE
                }
            }
        }
    }
}