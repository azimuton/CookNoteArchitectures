package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Zakuski(
    val dbzakuskititle: String,
    val dbzakuskicontent: String,
    val dbzakuskiimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}