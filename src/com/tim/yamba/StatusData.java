package com.tim.yamba;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Container of the operations of database.</br> It encapsulates the details of
 * {@link android.database.sqlite}. Use {@link YambaApplication#getStatusData}
 * to get the single instance of this application.
 * 
 * @see YambaApplication
 * @author Tim
 * 
 */
public class StatusData {
	final static String TAG = StatusData.class.getName();

	final static String DB_NAME = "timeline.db";
	final static int DB_VERSION = 1;
	final static String TABLE = "timeline";
	final static String C_ID = BaseColumns._ID;
	final static String C_CREATED_AT = "created_at";
	final static String C_USER = "user";
	final static String C_TEXT = "txt";
	final static String C_SOURCE = "source";

	/**
	 * We can use it to get db like 
	 * <ul>
	 * <li>{@link android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()} </li>
	 * <li>{@link android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()}</li>
	 * </ul>
	 */
	private final DbHelper dbHelper;

	class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
		 * .sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = String
					.format("create table %s ( %s int primary key, %s int , %s text , %s text, %s text)",
							TABLE, C_ID, C_CREATED_AT, C_USER, C_TEXT, C_SOURCE);
			db.execSQL(sql);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
		 * .sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sqlString = "drop table if exist " + TABLE;
			onCreate(db);
			Log.d(TAG, sqlString);

		}

	}

	/**
	 * Note:
	 * <ul>
	 * <li>context :Interface to global information about an application
	 * environment.</li>
	 * <li>context subclass : {@link android.app.Application},
	 * {@link android.app.Service},{@link android.app.Activity}</li>
	 * </ul>
	 * 
	 * @see android.content.Context
	 * @param context
	 */
	public StatusData(Context context) {
		dbHelper = new DbHelper(context);
	}

	/**
	 * Method to close {@link #dbHelper}
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Use {@link #dbHelper} to insert values. If the values cannot be inserted
	 * to the database because the key value conflicts. That is, any
	 * {@link android.database.SQLException} will be ignored.
	 * 
	 * @param values
	 *            is data to be inserted to database
	 * @see android.database.SQLException
	 */
	public void insertOrIgnore(ContentValues values) {
		SQLiteDatabase db = null;
		try {

			db = dbHelper.getWritableDatabase();
			db.insertWithOnConflict(TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		} catch (SQLException e) {
			// ignore
		} finally {
			db.close();
		}

	}

	/**
	 * 
	 * Note:
	 * <ul>
	 * <li>{@link android.database.Cursor} is a kind of data structure. It
	 * provides some method to randomly read and write.</li>
	 * </ul>
	 * 
	 * @see android.database.sqlite.SQLiteDatabase#query
	 * @return the all status of database ordered by time of status
	 */
	public Cursor getStatusUpdates() {
		SQLiteDatabase db = null;
		Cursor result = null;
		try {

			db = dbHelper.getReadableDatabase();
			result = db.query(TABLE, null, null, null, null, null, C_CREATED_AT
					+ " DESC");

		} catch (SQLException e) {
			// ignore
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 
	 * @return the timestamp of latest status, or
	 *         {@link java.lang.Long#MIN_VALUE} if no latest status.
	 */
	public long getLatestStatusCreatedTime() {
		SQLiteDatabase db = null;
		long result = Long.MIN_VALUE;
		Cursor tempCursor;
		try {

			db = dbHelper.getReadableDatabase();
			String query = String.format("SELECT MAX (%s) from %s",
					C_CREATED_AT, TABLE);
			Log.d(TAG, "query = " + query);
			tempCursor = db.rawQuery(query, null);
			result = tempCursor.moveToNext() ? tempCursor.getLong(0)
					: Long.MIN_VALUE;
		} catch (SQLException e) {
			// ignore
			Log.d(TAG, "in getLatestStatusCreatedTime() SQLException");
		} finally {
			db.close();
		}
		return result;

	}

	/**
	 * 
	 * @param id
	 *            is the value of "C_ID" column in database.
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public String getStatusTextById(long id) {
		SQLiteDatabase db = null;
		String result = null;
		Cursor tempCursor;
		try {

			db = dbHelper.getReadableDatabase();
			String query = String.format("SELECT %s FROM %s WHERE %s = %d",
					C_TEXT, TABLE, C_ID, id);
			Log.d(TAG, "query = " + query);
			tempCursor = db.rawQuery(query, null);
			result = tempCursor.moveToNext() ? tempCursor.getString(0) : null;
		} catch (SQLException e) {
			// ignore
			Log.d(TAG, "in getLatestStatusCreatedTime() SQLException");
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * @return the dbHelper
	 */
	public DbHelper getDbHelper() {
		return dbHelper;
	}


}
