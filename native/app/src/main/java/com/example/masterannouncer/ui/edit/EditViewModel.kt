package com.example.masterannouncer.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.repository.AnnouncementRoomDB
import com.example.masterannouncer.repository.Repository
import com.example.masterannouncer.service.Service
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: Repository
    val announcements: LiveData<List<Announcement>>

    init {

        val wordsDao = AnnouncementRoomDB.getDatabase(application,viewModelScope).announcementDAO()
        repository = Repository(wordsDao)
        announcements = repository.announcements
        Service.repository=repository
    }

    fun insert(announcement: Announcement) = viewModelScope.launch {
        repository.addAnnouncement(announcement)
    }

    fun delete(announcement: Announcement) = viewModelScope.launch {
        repository.deleteAnnouncement(announcement)
        Service.service.deleteAnnouncement(announcement.id*(-1))
    }

    fun update(announcement: Announcement)=viewModelScope.launch {
        repository.editAnnouncement(announcement)
        Service.service.updateAnnouncement(Announcement(announcement.id*(-1),announcement.title,announcement.desc))
    }

    fun insertToAdd(announcement: Announcement)=viewModelScope.launch {
        val anns=repository.getAnnouncementsStatic()
        val ann=anns[anns.size-1]
        repository.insertToAdd(ann.id)
    }
}