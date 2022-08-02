package com.jkdajac.data.roomstorage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Deserti(
    val dbdesertititle: String,
    val dbdeserticontent: String,
    val dbdesertiimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}