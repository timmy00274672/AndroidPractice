package com.tim.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	private Updater updater;
	private YambaApplication application;
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private static final String TAG = UpdaterService.class.getName();
	/**
	 * delay of {@link #updater}
	 */
	private static final int DELAY = 5000;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.d(UpdaterService.TAG, "in onCreate");
		super.onCreate();
		updater = new Updater();
		application = (YambaApplication) getApplication();
		Log.d(TAG, "before new dbHelper");
		dbHelper = new DbHelper(this);
		Log.d(TAG,"success");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(UpdaterService.TAG, "in onStartCommand");
		application.setUpdaterServiceRunning(true);
		updater.start();
		return super.onStartCommand(intent, flags, startId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(UpdaterService.TAG, "in onDestroy");
		application.setUpdaterServiceRunning(false);
		updater.interrupt();
		updater = null;
	}

	private class Updater extends Thread {

		/**
		 * @param threadName
		 */
		public Updater() {
			super("Updater");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			List<Status> timeline = null;
			while (application.getUpdaterServiceRunning()) {
				try {
					db = dbHelper.getWritableDatabase();
					ContentValues values = new ContentValues();

					timeline = application.getTwitter().getFriendsTimeline();
					for (Status status : timeline) {
			            // Insert into database
			            values.clear(); // <7>
			            values.put(DbHelper.C_ID, status.id);
			            values.put(DbHelper.C_CREATED_AT, status.createdAt.getTime());
			            values.put(DbHelper.C_SOURCE, status.source);
			            values.put(DbHelper.C_TEXT, status.text);
			            values.put(DbHelper.C_USER, status.user.name);
			            db.insertOrThrow(DbHelper.TABLE, null, values); // <8>

			            Log.d(TAG, String.format("%s: %s", status.user.name, status.text));

					}

					sleep(DELAY);
				} catch (TwitterException e) {
//					e.printStackTrace();
				} catch (InterruptedException e) {
//					e.printStackTrace();
					application.setUpdaterServiceRunning(false);
				} catch (SQLiteException e) {
					Log.d(TAG, "create db fail");
//					e.printStackTrace();
				} finally {
					db.close();
				}
			}
		}
	}
}
