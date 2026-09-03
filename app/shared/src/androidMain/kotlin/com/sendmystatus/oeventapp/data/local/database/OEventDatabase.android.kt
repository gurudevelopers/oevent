package com.sendmystatus.oeventapp.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<OEventDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("oevent.db")
    return Room.databaseBuilder<OEventDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
