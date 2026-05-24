package com.example.eipi.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.eipi.terms.ExamTermDao;
import com.example.eipi.terms.ExamTermEntity;

@Database(entities = {ExamTermEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract ExamTermDao examTermDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "eipi_database"
                    ).build();
                }
            }
        }

        return instance;
    }
}