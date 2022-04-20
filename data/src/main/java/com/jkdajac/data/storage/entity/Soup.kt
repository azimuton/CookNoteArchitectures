package com.jkdajac.data.storage.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Soup(
    val dbsouptitle: String,
    val dbsoupcontent: String,
    val dbsoupimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}