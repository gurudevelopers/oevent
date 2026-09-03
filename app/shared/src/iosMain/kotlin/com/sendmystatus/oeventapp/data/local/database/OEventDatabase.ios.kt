package com.sendmystatus.oeventapp.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<OEventDatabase> {
    val dbFilePath = NSHomeDirectory() + "/Documents/oevent.db"
    return Room.databaseBuilder<OEventDatabase>(
        name = dbFilePath,
        factory = { OEventDatabaseConstructor.initialize() }
    )
}
