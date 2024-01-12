package com.example.squlitetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "coursedb";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "mycourses";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String DURATION_COL = "duration";

    private static final String DESCRIPTION_COL = "description";

    private static final String TRACKS_COL = "tracks";

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DURATION_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + TRACKS_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewCourse(String Name, String email, String mobile, String gender) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, Name);
        values.put(DURATION_COL, email);
        values.put(DESCRIPTION_COL, mobile);
        values.put(TRACKS_COL, gender);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Event> readCourses() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Event> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new Event(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void updateCourse(String originalCourseName, String Name, String email,
                             String mobilen, String gender) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, Name);
        values.put(DURATION_COL, email);
        values.put(DESCRIPTION_COL, mobilen);
        values.put(TRACKS_COL, gender);

        db.update(TABLE_NAME, values, "name=?", new String[]{originalCourseName});
        db.close();
    }

    public void deleteCourse(String courseName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{courseName});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

