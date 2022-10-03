package com.alphaomardiallo.parisforkids.data.local

import androidx.room.*
import com.alphaomardiallo.parisforkids.data.model.queFaireAParis.EventsAndActivities
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsAndActivitiesDAO {

    @Insert
    suspend fun insertEventsAndActivities(eventsAndActivities: EventsAndActivities)

    @Update
    suspend fun updateEventsAndActivities(eventsAndActivities: EventsAndActivities)

    @Delete
    suspend fun deleteEventsAndActivities(eventsAndActivities: EventsAndActivities)

    @Query("SELECT * FROM EventsAndActivities_table")
    fun getEventsAndActivities(): Flow<List<EventsAndActivities>>
}