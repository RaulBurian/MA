package com.example.masterannouncer.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.domain.AnnouncementDAO
import com.example.masterannouncer.domain.AnnouncementId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Announcement::class,AnnouncementId::class], version = 1,exportSchema = false)
abstract class AnnouncementRoomDB: RoomDatabase() {

    abstract fun announcementDAO(): AnnouncementDAO

    companion object {
        @Volatile
        private var INSTANCE: AnnouncementRoomDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AnnouncementRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnnouncementRoomDB::class.java,
                    "annoucement_database"
                )
                    .addCallback(AnnouncementDBCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AnnouncementDBCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.announcementDAO())
//                    }
//                }
            }
        }

        suspend fun populateDatabase(wordDao: AnnouncementDAO) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            wordDao.deleteAll()

            var word = Announcement(1,"2","3")
            var word2 = Announcement(2,"21","3")
            var word3 = Announcement(3,"24","3")
            wordDao.insert(word)
            wordDao.insert(word2)
            wordDao.insert(word3)
        }


    }
}