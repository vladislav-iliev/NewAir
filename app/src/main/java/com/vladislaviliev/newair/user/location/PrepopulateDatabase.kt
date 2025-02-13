package com.vladislaviliev.newair.user.location

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class PrepopulateDatabase(private val city: UserLocation) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        val values = ContentValues().apply {
            put(UserLocation::id.name, city.id)
            put(UserLocation::name.name, city.name)
            put(UserLocation::latitude.name, city.latitude)
            put(UserLocation::longitude.name, city.longitude)
        }
        db.insert(UserLocation::class.java.simpleName, SQLiteDatabase.CONFLICT_REPLACE, values)
    }
}