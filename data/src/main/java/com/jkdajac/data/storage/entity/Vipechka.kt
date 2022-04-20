package com.jkdajac.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Vipechka(
    val dbvipechkatitle: String,
    val dbvipechkacontent: String,
    val dbvipechkaimage : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}