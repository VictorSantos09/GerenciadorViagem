package com.example.gerenciadorviagem.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gerenciadorviagem.entity.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrip(trip: Trip): Long

    @Update
    suspend fun updateTrip(trip: Trip)

    @Query("UPDATE Trip SET itinerary = :itinerary WHERE id = :id")
    suspend fun updateTripItineraryById(id: Int, itinerary: String)

    @Delete
    suspend fun delete(trip: Trip)

    @Query("SELECT * FROM Trip WHERE id = :id")
    suspend fun findById(id: Int): Trip?

    @Query("SELECT * FROM Trip ORDER BY startDate DESC")
    fun findAll(): Flow<List<Trip>>
}
