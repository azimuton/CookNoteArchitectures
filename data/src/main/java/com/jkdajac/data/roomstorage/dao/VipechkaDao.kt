package com.jkdajac.data.roomstorage.dao

import androidx.room.*
import com.jkdajac.data.roomstorage.entity.Vipechka

@Dao
interface VipechkaDao {
    @Query("SELECT * FROM vipechka")
    fun getAll(): List<Vipechka>

    @Query("DELETE FROM vipechka WHERE id IN (SELECT dbvipechkaimage FROM vipechka)")
    fun delImage()

    @Insert
    fun insertVipechka(vipechka : Vipechka)

    @Delete
    fun deleteVipechka(vipechka : Vipechka)

    @Update
    fun updateVipechka(vipechka : Vipechka)

    @Query("SELECT * FROM Vipechka WHERE id = :id")
    fun getVipechkaById(id: Int): Vipechka?
}