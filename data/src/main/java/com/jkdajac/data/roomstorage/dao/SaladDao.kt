package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Salad

@Dao
interface SaladDao {
    @Query("SELECT * FROM salad")
    fun getAll(): List<Salad>

    @Query("DELETE FROM salad WHERE id IN (SELECT dbsaladimage FROM salad)")
    fun delImage()

    @Insert
    fun insertSalad(salad : Salad)

    @Delete
    fun deleteSalad(salad : Salad)

    @Update
    fun updateSalad(salad : Salad)

    @Query("SELECT * FROM Salad WHERE id = :id")
    fun getSaladById(id: Int): Salad?
}