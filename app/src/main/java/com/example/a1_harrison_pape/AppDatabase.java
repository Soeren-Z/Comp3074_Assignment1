package com.example.a1_harrison_pape;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Payment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract PaymentDao paymentDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "payment_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
