package com.mvvm.dagger.sample.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database( entities = [User::class, Place::class, Photo::class], version = 1, exportSchema = false)
abstract class SampleDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
    abstract fun photoDao(): PhotoDao

    companion object {
        private const val DATABASE_NAME = "sample_database"
        fun buildDatabase(context: Context) = Room.databaseBuilder(context, SampleDataBase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
    }
}