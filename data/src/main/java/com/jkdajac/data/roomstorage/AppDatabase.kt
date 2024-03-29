package com.jkdajac.data.roomstorage

import android.content.Context
import androidx.room.*
import com.jkdajac.data.roomstorage.dao.*
import com.jkdajac.data.roomstorage.entity.*
import com.jkdajac.data.roomstorage.entity.Converter

@Database(entities = arrayOf(Meat::class, Soup::class, Zakuski::class, Salad::class, Deserti::class,
    Vipechka::class, Sous::class, Solenia::class, Napitki::class, Soveti::class), version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meatDao(): MeatDao
    abstract fun soupDao(): SoupDao
    abstract fun zakuskiDao(): ZakuskiDao
    abstract fun saladDao(): SaladDao
    abstract fun desertiDao(): DesertiDao
    abstract fun vipechkaDao(): VipechkaDao
    abstract fun sousDao(): SousDao
    abstract fun soleniaDao(): SoleniaDao
    abstract fun napitkiDao(): NapitkiDao
    abstract fun sovetiDao(): SovetiDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}