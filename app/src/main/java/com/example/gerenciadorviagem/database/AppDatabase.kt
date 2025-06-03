package com.example.gerenciadorviagem.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.entity.User
import com.example.gerenciadorviagem.dao.TripDao
import com.example.gerenciadorviagem.entity.Trip
import com.example.gerenciadorviagem.shared.utils.Converters

@Database (
    entities = [User::class, Trip::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        private val V1_CREATING_TABLES_USER_AND_TRIP = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                            CREATE TABLE IF NOT EXISTS User (
                                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                name TEXT NOT NULL,
                                password TEXT NOT NULL,
                                email TEXT NOT NULL
                            )
                        """
                )

                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS Trip (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            destination TEXT NOT NULL,
                            tripType TEXT NOT NULL,
                            startDate TEXT NOT NULL,
                            endDate TEXT NOT NULL,
                            budget TEXT NOT NULL
                        )
                    """
                )
            }
        }

        private val V2_ALTER_DATE_FIELDS = object : Migration(2, 3) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS Trip")

                db.execSQL(
                    """
            CREATE TABLE trip (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                destination TEXT NOT NULL,
                tripType TEXT NOT NULL,
                startDate INTEGER NOT NULL,
                endDate INTEGER NOT NULL,
                budget REAL NOT NULL
            )
            """
                )
            }
        }


        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                //context.deleteDatabase("user_database")
                Room.databaseBuilder(context, AppDatabase::class.java,
                    "user_database")
                    .addMigrations(
                        V1_CREATING_TABLES_USER_AND_TRIP,
                        V2_ALTER_DATE_FIELDS
                    )
                    .build().also { Instance = it }
            }
        }
    }
}