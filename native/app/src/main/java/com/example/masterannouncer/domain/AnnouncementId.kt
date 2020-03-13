package com.example.masterannouncer.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "announcement_id")
data class AnnouncementId(
    @PrimaryKey val id:Long
)