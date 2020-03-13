package com.example.masterannouncer.domain

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnnouncementDAO {

    @Query("SELECT * from announcement_table ORDER BY id ASC")
    fun getAnnouncements(): LiveData<List<Announcement>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(announcement: Announcement)

    @Query("DELETE FROM announcement_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteAnnouncement(announcement: Announcement)

    @Update
    suspend fun updateAnnouncement(announcement: Announcement)

    @Query("SELECT * from announcement_id ORDER BY id ASC")
    suspend fun getAnnouncementsToSync(): List<AnnouncementId>

    @Insert
    suspend fun insertToAdd(announcementId: AnnouncementId)

    @Query("DELETE FROM announcement_id")
    suspend fun deleteAllToAdd()

    @Query("SELECT * from announcement_table ORDER BY id ASC")
    suspend fun getAnnouncementsStatic(): List<Announcement>

}