package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Soup

@Dao
interface SoupDao {
    @Query("SELECT * FROM soup")
    fun getAll(): List<Soup>

    @Query("DELETE FROM soup WHERE id IN (SELECT dbsoupimage FROM soup)")
    fun delImage()

    @Insert
    fun insertSoup(soup : Soup)

    @Delete
    fun deleteSoup(soup : Soup)

    @Update
    fun updateSoup(soup : Soup)

    @Query("SELECT * FROM Soup WHERE id = :id")
    fun getSoupById(id: Int): Soup?
}