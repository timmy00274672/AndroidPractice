package com.tim.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * This is an {@link android.app.Application} object. It provides shared
 * attributes and methods (like prefs, {@link #getTwitter()}).
 * 
 * @author Tim
 * @see android.app.Application
 */
public class YambaApplication extends Application implements
		OnSharedPreferenceChangeListener {
	public static final String UPDATE_PERMISSION = "com.tim.yamba.updatePermission";
	private static final String TAG = YambaApplication.class.getName();
	/**
	 * Indicate whether {@link UpdaterService} is running.
	 */
	/**
	 * Store username, password, and apiRoot
	 */
	private SharedPreferences prefs;
	/**
	 * Connect to apiRoot value in {@link #prefs}
	 */
	private Twitter twitter;
	private StatusData statusData;

	/**
	 * Initialize {@link #prefs}
	 * 
	 * @see android.app.Application#onCreate()
	 * @see android.preference.PreferenceManager#getDefaultSharedPreferences(android.content.Context)
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		this.statusData = new StatusData(this);
	}

	/**
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public synchronized Twitter getTwitter() {
		if (twitter == null) {
			String username, password, apiRoot;
			username = prefs.getString("username", "student");
			password = prefs.getString("password", "password");
			apiRoot = prefs.getString("aipRoot",
					getString(R.string.defaultApiRoot));
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(apiRoot);
			Log.d("in getTwitter", twitter.getScreenName() + "/" + password
					+ "/" + apiRoot);
		}
		return twitter;

	}

	/**
	 * Set {@link #twitter} as null, because {@link #getTwitter()} initializes
	 * {@link #twitter} when it's null.
	 * 
	 * @see #getTwitter()
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		this.twitter = null;

	}

//	/**
//	 * Setter of {@link #runBoolean}
//	 * 
//	 * @param run
//	 */
//	public void setUpdaterServiceRunning(boolean run) {
//		this.runBoolean = run;
//		if (run) {
//			startService(new Intent(this, UpdaterService.class));
//		} else {
//			stopService(new Intent(this, UpdaterService.class));
//		}
//
//	}
//
//	public boolean getUpdaterServiceRunning() {
//		return this.runBoolean;
//
//	}

	public StatusData getStatusData() {
		return statusData;
	}

	/**
	 * fetch new timeline status into database (via {@link StatusData#dbHelper})
	 * We have to put the method here instead of {@link #statusData} because we
	 * need {@link #twitter} object (We cannot use
	 * {@link Service#getApplication()} in {@link #statusData} because it does
	 * not extends )
	 * 
	 * @return the numbers of new statuses stored into database
	 */
	public synchronized int fetchStatusUpdates() {
		Log.d(TAG, "in fetchStatusUpdates()");
		List<Status> statusList = null;
		try {
			statusList = getTwitter().getFriendsTimeline();
		} catch (TwitterException e) {
			stopService(new Intent(this,UpdaterService.class));
			Log.e(TAG, e.toString());
			return 0;
		} catch (Exception e) {
			Log.e(TAG, "other runtime exception");
		}
		Log.d(TAG, "between try blocks in fetchStatusUpdates()");
		ContentValues values = new ContentValues();
		int count = 0;
		final long lastTime = getStatusData().getLatestStatusCreatedTime();
		for (Status status : statusList) {
			try {
				// Insert into database
				values.clear(); // <7>
				values.put(StatusData.C_ID, status.id);
				long createdAt = status.createdAt.getTime();
				values.put(StatusData.C_CREATED_AT, createdAt);
				values.put(StatusData.C_SOURCE, status.source);
				values.put(StatusData.C_TEXT, status.text);
				values.put(StatusData.C_USER, status.user.name);
				// db.insertOrThrow(DbHelper.TABLE, null, values); //
				// <8>
				statusData.insertOrIgnore(values);
				// Log.d(YambaApplication.TAG,
				// String.format("%s: %s", status.user.name, status.text));
				if (createdAt > lastTime)
					count++;
			} catch (RuntimeException e) {
				Log.e(TAG, e.toString());
			}

		}
		return count;
	}

	/**
	 * @return the prefs
	 */
	public SharedPreferences getPrefs() {
		return prefs;
	}

	public boolean isUpdaterServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (UpdaterService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}
