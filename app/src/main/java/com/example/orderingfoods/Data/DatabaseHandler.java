package com.example.orderingfoods.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.orderingfoods.Models.User;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User.db";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_TABLE_NAME_USER = "User";
    public static final String KEY_TABLE_ID_USER = "id";
    public static final String KEY_TABLE_USERNAME_USER = "username";
    public static final String KEY_TABLE_PASSWORD_USER = "password";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + KEY_TABLE_NAME_USER + " (" +
                KEY_TABLE_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TABLE_USERNAME_USER + " TEXT, " +
                KEY_TABLE_PASSWORD_USER + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE_NAME_USER);
            onCreate(db);
        }
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_USERNAME_USER, user.getUsername());
        values.put(KEY_TABLE_PASSWORD_USER, user.getPassword());

        long result = db.insert(KEY_TABLE_NAME_USER, null, values);
        if (result == -1) {
            Log.e("DatabaseHandler", "Error inserting user: " + user.getUsername());
        } else {
            Log.d("DatabaseHandler", "User added successfully: " + user.getUsername());
        }
        db.close();
    }

    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(KEY_TABLE_NAME_USER, new String[]{KEY_TABLE_ID_USER, KEY_TABLE_USERNAME_USER, KEY_TABLE_PASSWORD_USER},
                KEY_TABLE_USERNAME_USER + "=?", new String[]{username}, null, null, null);
    }
}