package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Zakuski

@Dao
interface ZakuskiDao {
    @Query("SELECT * FROM zakuski")
    fun getAll(): List<Zakuski>

    @Query("DELETE FROM zakuski WHERE id IN (SELECT dbzakuskiimage FROM zakuski)")
    fun delImage()

    @Insert
    fun insertZakuski(zakuski : Zakuski)

    @Delete
    fun deleteZakuski(zakuski : Zakuski)

    @Update
    fun updateZakuski(zakuski : Zakuski)

    @Query("SELECT * FROM Zakuski WHERE id = :id")
    fun getZakuskiById(id: Int): Zakuski?
}