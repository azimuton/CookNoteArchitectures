package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Meat

@Dao
interface MeatDao {
    @Query("SELECT * FROM meat")
    fun getAll(): List<Meat>

    @Query("DELETE FROM meat WHERE id IN (SELECT dbmeatimage FROM meat)")
    fun delImage()

    @Insert
    fun insertMeat(meat: Meat)

    @Delete
    fun deleteMeat(meat: Meat)

    @Update
    fun updateMeat(meat: Meat)

    @Query("SELECT * FROM Meat WHERE id = :id")
    fun getMeatById(id: Int): Meat?
}
