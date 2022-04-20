package com.jkdajac.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Meat(
    val dbmeattitle: String,
    val dbmeatcontent: String,
    val dbmeatimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
