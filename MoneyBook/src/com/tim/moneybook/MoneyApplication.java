package com.tim.moneybook;

import android.app.Application;
import android.util.Log;

public class MoneyApplication extends Application {
	private static final String TAG = MoneyApplication.class.getName();
	private DbHelper dbHelper;
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "in onCreate()");
		dbHelper = new DbHelper(this);
	}
	/**
	 * @return the dbHelper
	 */
	public DbHelper getDbHelper() {
		return dbHelper;
	}
	

	
}
