package com.tim.notepadwithcp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String C1_TABLE_NAME = "C1_TABLE_NAME";
	public static final String C1_NAME = "C1_NAME";
	private static final String _DB_DATABASE_NAME = "book.db";
	private static final int _DB_VERSION = 2;
	private static final String TAG = DBHelper.class.getSimpleName();
	private String[] mC1DefalutStrings;

	public DBHelper(Context context) {
		super(context, _DB_DATABASE_NAME, null, _DB_VERSION);
		mC1DefalutStrings = context.getResources().getStringArray(
				R.array.C1Defalut);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String temp = String
				.format("CREATE TABLE %s ( _id INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50) );",
						C1_TABLE_NAME, C1_NAME);
		Log.v(TAG, String.format("onCreate : temp = %s", temp));
		db.execSQL(temp);
		init(db);

	}

	public void init(SQLiteDatabase db) {
		for (String c1Value : mC1DefalutStrings) {
			ContentValues values = new ContentValues();
			values.put(C1_NAME, c1Value);
			db.insert(C1_TABLE_NAME, null, values);
			Log.v(TAG,
					String.format("onCreate, value(%s) is inserted", c1Value));
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + C1_TABLE_NAME);
		onCreate(db);
	}

}
