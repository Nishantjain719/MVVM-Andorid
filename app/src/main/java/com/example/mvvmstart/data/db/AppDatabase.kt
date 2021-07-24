package com.example.mvvmstart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmstart.data.db.entities.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao() : UserDao

    companion object{

        @Volatile
        private var instance: AppDatabase? = null // volatile Anotation means this var is visible to all threads.
        private val LOCK = Any() // this is to make sure we don't create 2 instances of our db.

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "MyDatabase.db").build()
    }
}