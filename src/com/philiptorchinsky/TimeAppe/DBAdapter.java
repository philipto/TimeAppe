package com.philiptorchinsky.TimeAppe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
    public final String KEY_ROWID = "_id";
    public final String KEY_PROJECT = "project";
    public final String KEY_STATUS = "status";
    public final String KEY_TIMESPENT = "timespent";
    public final String KEY_LASTACTIVATED = "lastactivated";
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "timecard";
    private static final String DATABASE_TABLE = "projects";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "create table IF NOT EXISTS `" + DATABASE_TABLE + "` (_id integer primary key autoincrement, "
                    + "project text not null, status text not null, "
                    + "timespent long, "
                    + "lastactivated long);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    public void destroy() {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }


    //---insert a title into the database---
    public long insert(String project, String status, long timespent, long lastactivated) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROJECT, project);
        initialValues.put(KEY_STATUS, status);
        initialValues.put(KEY_TIMESPENT, timespent);
        initialValues.put(KEY_LASTACTIVATED, lastactivated);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteById(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //---retrieves all the data---
    public Cursor getAll() {
        return db.query(DATABASE_TABLE, new String[]{
                KEY_ROWID,
                KEY_PROJECT,
                KEY_STATUS,
                KEY_TIMESPENT,
                KEY_LASTACTIVATED},
                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular row---
    public Cursor getrow(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_PROJECT,
                        KEY_STATUS,
                        KEY_TIMESPENT,
                        KEY_LASTACTIVATED
                },
                        KEY_ROWID + "=" + rowId,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a row---
    public boolean update(long rowId, String project,
                          String status, long timespent, long lastactivated) {
        ContentValues args = new ContentValues();
        args.put(KEY_PROJECT, project);
        args.put(KEY_STATUS, status);
        args.put(KEY_TIMESPENT, timespent);
        args.put(KEY_LASTACTIVATED, lastactivated);
        return db.update(DATABASE_TABLE, args,
                KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void updateSpentTimeByProject (String project, String status,
                           long timespent) {
        String query = "UPDATE " + DATABASE_TABLE + " SET " + KEY_TIMESPENT + "=" + timespent + "," +
                KEY_STATUS + "='" + status + "' Where " + KEY_PROJECT + " = '" + project + "'";
        try {
            db.execSQL(query);
        }
        catch (Exception e) {
            Toast.makeText(context, "Query to update timespent field failed "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void updateActivatedByProject (String project, String status,
                                          long lastactivated) {


        String query = "UPDATE " + DATABASE_TABLE + " SET " + KEY_LASTACTIVATED + "=" + lastactivated + "," +
                KEY_STATUS + "='" + status + "' Where " + KEY_PROJECT + " = '" + project + "'";
        try {
            db.execSQL(query);
        }
        catch (Exception e) {
            Toast.makeText(context, "Query to update lastactivated field failed "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
