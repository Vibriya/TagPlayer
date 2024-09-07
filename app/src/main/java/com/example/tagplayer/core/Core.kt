package com.example.tagplayer.core

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.tagplayer.all.data.ExtractMedia
import com.example.tagplayer.core.domain.DispatcherList
import com.example.tagplayer.core.data.database.MediaDatabase
import com.example.tagplayer.core.data.MediaStoreHandler
import com.example.tagplayer.core.data.ObserveMediaBroadcast
import com.example.tagplayer.core.domain.ProvideMediaStoreHandler
import com.example.tagplayer.core.data.database.dao.SongsDao
import com.example.tagplayer.core.data.ForegroundWrapper
import com.example.tagplayer.core.domain.ManageResources
import com.example.tagplayer.core.domain.ProvideLastPlayedDao
import com.example.tagplayer.core.domain.ProvideTagDao

interface Core : ProvideMediaStoreHandler, ManageResources.Provide, ProvideLastPlayedDao, ProvideTagDao {

    fun songsDao() : SongsDao
    fun observeMediaBroadcast() : ObserveMediaBroadcast
    fun mediaDatabase() : MediaDatabase
    fun foregroundWrapper() : ForegroundWrapper

    class Base(
        context: Context,
        contentResolver: ContentResolver
    ): Core {
        private val mediaDatabase = Room.databaseBuilder(
            context,
            MediaDatabase::class.java,
            "MediaDatabase.db"
        ).build()

        private val foregroundWrapper = ForegroundWrapper.Base(WorkManager.getInstance(context))


        private val mediaStoreHandler = MediaStoreHandler.Base(
            ExtractMedia.Base(contentResolver),
            mediaDatabase.songsDao
        )

        private val observeMediaBroadcast = ObserveMediaBroadcast(DispatcherList.Base, songsDao())
        private val manageResources = ManageResources.Base(context)



        override fun songsDao() = mediaDatabase.songsDao
        override fun lastPlayedDao() = mediaDatabase.lastPlayed
        override fun tagDao() = mediaDatabase.tagDao

        override fun manageRecourses() = manageResources
        override fun observeMediaBroadcast() = observeMediaBroadcast
        override fun mediaDatabase()= mediaDatabase

        override fun foregroundWrapper() = foregroundWrapper

        override fun mediaStoreHandler() = mediaStoreHandler

    }

}
