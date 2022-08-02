package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Sous(
    val dbsoustitle: String,
    val dbsouscontent: String,
    val dbsousimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}