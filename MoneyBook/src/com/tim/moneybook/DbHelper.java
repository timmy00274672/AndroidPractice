package com.tim.moneybook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TABLE_NAME = "T_MONEY";
	private static final int DB_Version = 2;
	private static final String TAG = DbHelper.class.getName();
	private static final String C_DATE = "C_DATE";
	private static final String C_AMOUNT = "C_AMOUNT";
	private static final String C_PS = "C_PS";
	private static final String DB_FILE_NAME = "money.db";
	private static final String C_ID = "C_ID";

	public DbHelper(Context context) {
		super(context, DB_FILE_NAME, null, DB_Version);

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format(
				"CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s text, %s text, %s text);",
				TABLE_NAME,C_ID,C_DATE,C_AMOUNT,C_PS);
		Log.d(TAG, String.format("in onCreate() sql = %s", sql));
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG,"onUpdgade()");
		String sql = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME);
		db.execSQL(sql);
		onCreate(db);
		
	}
	
	public void insertData(String time,String amount, String ps){
		SQLiteDatabase wDb = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put(C_DATE, time);
		values.put(C_AMOUNT, amount);
		values.put(C_PS, ps);
		wDb.insert(TABLE_NAME, null, values );
	}

}
