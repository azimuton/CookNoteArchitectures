package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Napitki(
    val dbnapitkititle: String,
    val dbnapitkicontent: String,
    val dbnapitkiimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}