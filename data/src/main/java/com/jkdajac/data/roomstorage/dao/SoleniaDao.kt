package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Solenia

@Dao
interface SoleniaDao {
    @Query("SELECT * FROM solenia")
    fun getAll(): List<Solenia>

    @Query("DELETE FROM solenia WHERE id IN (SELECT dbsoleniaimage FROM solenia)")
    fun delImage()

    @Insert
    fun insertSolenia(solenia : Solenia)

    @Delete
    fun deleteSolenia(solenia : Solenia)

    @Update
    fun updateSolenia(solenia : Solenia)

    @Query("SELECT * FROM Solenia WHERE id = :id")
    fun getSoleniaById(id: Int): Solenia?
}