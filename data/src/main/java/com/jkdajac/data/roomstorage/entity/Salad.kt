package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Salad(
    val dbsaladtitle: String,
    val dbsaladcontent: String,
    val dbsaladimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}