package com.jkdajac.data.storage.dao

import androidx.room.*
import com.jkdajac.data.storage.entity.Deserti

@Dao
interface DesertiDao {
    @Query("SELECT * FROM deserti")
    fun getAll(): List<Deserti>

    @Query("DELETE FROM deserti WHERE id IN (SELECT dbdesertiimage FROM deserti)")
    fun delImage()

    @Insert
    fun insertDeserti(deserti : Deserti)

    @Delete
    fun deleteDeserti(deserti : Deserti)

    @Update
    fun updateDeserti(deserti : Deserti)

    @Query("SELECT * FROM Deserti WHERE id = :id")
    fun getDesertiById(id: Int): Deserti?
}