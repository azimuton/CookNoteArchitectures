package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Soveti(
    val dbsovetititle: String,
    val dbsoveticontent: String,
    val dbsovetiimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}