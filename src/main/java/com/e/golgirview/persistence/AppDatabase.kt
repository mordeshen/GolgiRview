package com.e.golgirview.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.e.golgirview.model.ItemModel


@Database(entities = [ItemModel::class], version = 3)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getMainDao(): MainDao


    companion object{
        const val DATABASE_NAME = "app_db"
//        var appDatabase: AppDatabase?=null
//
//        fun getDatabase(mContext: Context): AppDatabase {
//
//            if (appDatabase == null) {
//                appDatabase = Room.databaseBuilder(
//                    mContext.applicationContext,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                )
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//
//            return appDatabase as AppDatabase
//
//        }
    }


}