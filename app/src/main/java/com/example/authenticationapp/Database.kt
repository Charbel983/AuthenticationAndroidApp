package com.example.authenticationapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity

open class Database(db: SQLiteDatabase?) : AppCompatActivity() {

    private val database = db

    fun createTable(){
        database?.execSQL("CREATE TABLE IF NOT EXISTS usersDB(firstName TEXT, lastName TEXT, email TEXT, password TEXT, profile TEXT)")
    }
    fun addData(firstName : String, lastName : String, email : String, password : String, defaultImage : String){
        database?.execSQL("INSERT INTO usersDB(firstName, lastName, email, password, profile) VALUES('$firstName', '$lastName', '$email', '$password', '$defaultImage')")
    }
    fun updateName(firstName: String, lastName: String, email : String?){
        val cv = ContentValues()
        cv.put("firstName", firstName)
        cv.put("lastName", lastName)
        database?.update("usersDB", cv, "email = '$email'", null)
    }
    fun updatePassword(password: String, email: String?){
        val cv = ContentValues()
        cv.put("password", password)
        database?.update("usersDB", cv, "email = '$email'", null)
    }
    fun updateProfilePicture(picture: String, email: String?){
        val cv = ContentValues()
        cv.put("profile", picture)
        database?.update("usersDB", cv, "email = '$email'", null)
    }
    fun getProfilePicture(email : String?) : String{
        val cursor = database?.rawQuery("SELECT profile FROM usersDB WHERE email = '$email'", null)
        var profile = ""
        if(cursor?.count!! > 0){
            cursor.moveToFirst()
            profile = cursor.getString(0)
        }
        cursor.close()
        return profile
    }
    fun getPassword(email : String?) : String{
        val cursor = database?.rawQuery("SELECT password FROM usersDB WHERE email = '$email'", null)
        var password = ""
        if(cursor?.count!! > 0){
            cursor.moveToFirst()
            password = cursor.getString(0)
        }
        cursor.close()
        return password
    }
    fun emailAlreadyExists(emailCheck : String) : Boolean {
        try{
            val cursor = database?.rawQuery("SELECT email FROM usersDB WHERE email = '$emailCheck'", null)
            if(cursor?.moveToFirst()!!){
                database?.close()
                return true
            }
        }catch(errorException : Exception){
            database?.close()
        }
        return false
    }
    fun emailAndPasswordCheck(emailCheck : String, passwordCheck : String) : Boolean {
        try{
            val cursor = database?.rawQuery("SELECT email FROM usersDB WHERE email = '$emailCheck' AND password = '$passwordCheck'", null)
            if(cursor?.moveToFirst()!!){
                database?.close()
                return true
            }
        }catch(errorException : Exception){
            database?.close()
        }
        return false
    }
    fun getFirstName (email: String?) : String {
        val cursor = database?.rawQuery("SELECT firstName FROM usersDB WHERE email = '$email'", null)
        var firstName = ""
        if(cursor?.count!! > 0){
            cursor.moveToFirst()
            firstName = cursor.getString(0)
        }
        cursor.close()
        return firstName
    }
    fun getLastName (email : String?) : String {
        val cursor = database?.rawQuery("SELECT lastName FROM usersDB WHERE email = '$email'", null)
        var lastName = ""
        if(cursor?.count!! > 0){
            cursor.moveToFirst()
            lastName = cursor.getString(0)
        }
        cursor.close()
        return lastName
    }
}