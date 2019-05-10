package com.example.roomdatabase.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PersonModel.class} , version = 1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PersonDao personDao();


//    private static AppDatabase INSTANCE;
//    static AppDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE =
//                            Room.databaseBuilder(context.getApplicationContext(),
//                                    AppDatabase.class,
//                                    "product_database").build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
