package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Napitki

@Dao
interface NapitkiDao {
    @Query("SELECT * FROM napitki")
    fun getAll(): List<Napitki>

    @Query("DELETE FROM napitki WHERE id IN (SELECT dbnapitkiimage FROM napitki)")
    fun delImage()

    @Insert
    fun insertNapitki(napitki : Napitki)

    @Delete
    fun deleteNapitki(napitki : Napitki)

    @Update
    fun updateNapitki(napitki : Napitki)

    @Query("SELECT * FROM Napitki WHERE id = :id")
    fun getNapitkiById(id: Int): Napitki?
}