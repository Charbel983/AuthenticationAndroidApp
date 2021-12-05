package com.example.authenticationapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDAO {
    @Query("SELECT firstName FROM users WHERE email = :email")
    fun getFirstName(email : String?) : String

    @Query("SELECT lastName FROM users WHERE email =  :email")
    fun getLastName(email : String?) : String

    @Query("SELECT profilePicture FROM users WHERE email = :email")
    fun getProfilePicture(email : String?) : String

    @Query("SELECT password FROM users WHERE email = :email")
    fun getPassword(email: String?) : String

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email)")
    fun emailAlreadyExists(email: String?) : Boolean

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email AND password = :password)")
    fun emailAndPasswordCheck(email: String?, password : String?) : Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(vararg user : User)

    @Query("UPDATE users SET firstName = :newFirstName, lastName = :newLastName WHERE email = :email")
    fun updateName(email: String?, newFirstName : String, newLastName : String)

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    fun updatePassword(email: String?, newPassword : String)

    @Query("UPDATE users SET profilePicture = :newProfilePic WHERE email = :email")
    fun updateProfilePicture(email: String?, newProfilePic : String)
}