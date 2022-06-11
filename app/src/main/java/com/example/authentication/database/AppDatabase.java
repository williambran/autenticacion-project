package com.example.authentication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.authentication.database.dao.CredentialDAO;
import com.example.authentication.database.entity.Credential;

@Database(entities = {
        Credential.class
},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static  AppDatabase INSTANCE;

    public abstract CredentialDAO credentialDAO();

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"unam.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }


        return  INSTANCE;
    }


}
