package com.jkdajac.data.storage.dao

import androidx.room.*
import com.jkdajac.data.storage.entity.Soveti

@Dao
interface SovetiDao {
    @Query("SELECT * FROM soveti")
    fun getAll(): List<Soveti>

    @Query("DELETE FROM soveti WHERE id IN (SELECT dbsovetiimage FROM soveti)")
    fun delImage()

    @Insert
    fun insertSoveti(soveti : Soveti)

    @Delete
    fun deleteSoveti(soveti : Soveti)

    @Update
    fun updateSoveti(soveti : Soveti)

    @Query("SELECT * FROM Soveti WHERE id = :id")
    fun getSovetiById(id: Int): Soveti?
}