package com.jkdajac.data.storage.dao

import androidx.room.*
import com.jkdajac.data.storage.entity.Sous

@Dao
interface SousDao {
    @Query("SELECT * FROM sous")
    fun getAll(): List<Sous>

    @Query("DELETE FROM sous WHERE id IN (SELECT dbsousimage FROM sous)")
    fun delImage()

    @Insert
    fun insertSous(sous : Sous)

    @Delete
    fun deleteSous(sous : Sous)

    @Update
    fun updateSous(sous : Sous)

    @Query("SELECT * FROM Sous WHERE id = :id")
    fun getSousById(id: Int): Sous?
}