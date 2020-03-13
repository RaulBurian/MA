package com.example.masterannouncer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.domain.AnnouncementDAO
import com.example.masterannouncer.domain.AnnouncementId
import java.util.stream.Collectors

class Repository(private val roomDAO: AnnouncementDAO) {

    val announcements: LiveData<List<Announcement>> = roomDAO.getAnnouncements()


    suspend fun addAnnouncement(announcement: Announcement){
        roomDAO.insert(announcement)
    }

    suspend fun editAnnouncement(announcement: Announcement){
        roomDAO.updateAnnouncement(announcement)
    }


    suspend fun deleteAnnouncement(announcement: Announcement){
        roomDAO.deleteAnnouncement(announcement)
    }

    suspend fun deleteAll(){
        roomDAO.deleteAll();
    }

    suspend fun getToAdd(): List<AnnouncementId>{
        return roomDAO.getAnnouncementsToSync()
    }

    suspend fun insertToAdd(id:Long){
        return roomDAO.insertToAdd(AnnouncementId(id))
    }

    suspend fun deleteAllToAdd(){
        roomDAO.deleteAllToAdd()
    }

    suspend fun getAnnouncementsStatic():List<Announcement>{
        return roomDAO.getAnnouncementsStatic()
    }
}