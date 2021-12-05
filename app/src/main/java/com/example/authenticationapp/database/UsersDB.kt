package com.example.authenticationapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UsersDB : RoomDatabase() {

    abstract fun getUsersDAO() : UsersDAO

    companion object {

//        @Volatile
//        private var instance : UsersDB? = null
        fun get(context: Context) : UsersDB {
            return buildDatabase(context)
        }
        private fun buildDatabase(context: Context) : UsersDB {
            return Room.databaseBuilder(context, UsersDB::class.java, "usersDatabase").allowMainThreadQueries().build()
        }
    }
}