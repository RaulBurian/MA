package com.example.masterannouncer.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.repository.AnnouncementRoomDB
import com.example.masterannouncer.repository.Repository
import com.example.masterannouncer.service.Service
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

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
    }

    fun update(announcement: Announcement)=viewModelScope.launch {
        repository.editAnnouncement(announcement)
    }
}