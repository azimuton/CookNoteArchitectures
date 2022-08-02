package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Solenia(
    val dbsoleniatitle: String,
    val dbsoleniacontent: String,
    val dbsoleniaimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}