package com.jkdajac.cooknotearchitectures.presentation.editactivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.VipechkaFragment
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Vipechka
import kotlinx.android.synthetic.main.activity_edit_vipechka.*

class EditVipechkaActivity : AppCompatActivity() {

    lateinit var vipechkaDatabase: AppDatabase
    private val Pick_image = 1
    var tempImageUri = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vipechka)
        getMyIntents()

        vipechkaDatabase = AppDatabase.getDatabase(this)

        ivEditEditVipechka.setOnClickListener {
            chooseImage()
        }

        floatingVipechkaSaved.setOnClickListener {

            if (etEditVipechkaTitle.text.isNotEmpty() && etEditVipechkaContent.text.isNotEmpty()) {
                val title: String = etEditVipechkaTitle.text.toString()
                val content: String = etEditVipechkaContent.text.toString()
                val imageUri = tempImageUri


                val vipechka =
                    Vipechka(dbvipechkatitle = title, dbvipechkacontent = content, dbvipechkaimage = imageUri)
                Toast.makeText(this, "Ваш рецепт записан !", Toast.LENGTH_LONG).show()
                vipechkaDatabase.vipechkaDao().insertVipechka(vipechka)

                val intent = Intent(this, VipechkaFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0, R.anim.open_activity)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните пустые поля !", Toast.LENGTH_LONG)
                    .show()
            }
        }
        fabAddGalleryEditVipechka.setOnClickListener {
            clickFabAddImage()
        }
        ivDeleteEditVipechka.setOnClickListener {
            clickDeleteImage()
        }
    }

    fun clickDeleteImage() {
        vipechkaDatabase.vipechkaDao().delImage()
        cvEditVipechka.visibility = View.GONE
        fabAddGalleryEditVipechka.visibility = View.VISIBLE
    }

    fun clickFabAddImage(){
        cvEditVipechka.visibility = View.VISIBLE
        fabAddGalleryEditVipechka.visibility = View.GONE
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
            ivEditVipechka.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
    fun getMyIntents() {
        cvEditVipechka.visibility = View.GONE
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstance.I_NAME_KEY) != null) {
                //fabAddGalleryEditVipechka.visibility = View.GONE
                etEditVipechkaTitle.setText(i.getStringExtra(MyIntentConstance.I_NAME_KEY))
                etEditVipechkaContent.setText(i.getStringExtra(MyIntentConstance.I_CONTENT_KEY))
                if(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY) != "empty"){
                    cvEditVipechka.visibility = View.VISIBLE
                    ivEditVipechka.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_IMAGE_KEY)))
                } else {
                    cvEditVipechka.visibility = View.GONE
                }
            }
        }
    }
}