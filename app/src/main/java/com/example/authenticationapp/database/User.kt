package com.example.authenticationapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//data class User(
//    val firstName : String,
//    val lastName : String,
//    val email : String,
//    val password : String,
//    val profilePicture : String
//)
@Entity(tableName = "users")
data class User (
//    @PrimaryKey(autoGenerate = true)
//    val id : Int,
    @ColumnInfo(name = "firstName")
    val firstName : String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "profilePicture")
    val profilePicture: String
)