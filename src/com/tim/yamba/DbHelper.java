package com.tim.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private final static String TAG = DbHelper.class.getName();

	final static String DB_NAME = "timeline.db";
	final static int DB_VERSION = 1;
	final static String TABLE = "timeline";
	final static String C_ID = BaseColumns._ID;
	final static String C_CREATED_AT = "created_at";
	final static String C_USER = "user";
	final static String C_TEXT = "txt";
	final static String C_SOURCE = "source";

	Context context;

	public DbHelper(Context context) {

		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s ( %s int primary key, %s int , %s text , %s text, %s text)",
						TABLE, C_ID, C_CREATED_AT, C_USER, C_TEXT, C_SOURCE);
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sqlString = "drop table if exist " + TABLE;
		onCreate(db);
		Log.d(TAG, sqlString);
	}

}
