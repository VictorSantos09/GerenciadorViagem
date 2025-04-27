package com.example.gerenciadorviagem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.entity.User
import com.example.gerenciadorviagem.dao.TripDao
import com.example.gerenciadorviagem.entity.Trip

@Database (
    entities = [User::class, Trip::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                //context.deleteDatabase("user_database")
                Room.databaseBuilder(context, AppDatabase::class.java,
                    "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}