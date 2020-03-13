package com.example.masterannouncer.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "announcement_table")
data class Announcement (

    @SerializedName("id") @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("desc") var desc: String
)