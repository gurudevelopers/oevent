package com.sendmystatus.oeventapp.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<OEventDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "oevent.db")
    return Room.databaseBuilder<OEventDatabase>(
        name = dbFile.absolutePath,
    )
}
