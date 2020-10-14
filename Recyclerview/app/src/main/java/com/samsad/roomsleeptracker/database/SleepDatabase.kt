package com.samsad.roomsleeptracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract val sleepDatabaseDao: SleepDatabaseDao

    /* database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")*/


    companion object {
        //Volatile keyword will help to get values from memory and block caching
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context): SleepDatabase {
            //Multiple threads can ask for database instance at same time.
            //Wrapping in synchronised only one thread of execution at a time can
            //enter this block of code which makes sure db gets initialise only once
            synchronized(this) {
                var instance = INSTANCE

                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE daily_sleep_quality_table ADD COLUMN sleep_duration INTEGER")
                    }
                }

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                    INSTANCE = instance
                }

                return instance
            }

        }
    }
}