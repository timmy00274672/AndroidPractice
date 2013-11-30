package com.tim.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
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
	/**
	 * Indicate whether {@link UpdaterService} is running.
	 */
	private boolean runBoolean = false;
	/**
	 * Store username, password, and apiRoot
	 */
	private SharedPreferences prefs;
	/**
	 * Connect to apiRoot value in {@link #prefs}
	 */
	private Twitter twitter;

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
			username = prefs.getString("username", "");
			password = prefs.getString("password", "");
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

	/**
	 * Setter of {@link #runBoolean}
	 * 
	 * @param run
	 */
	public void setUpdaterServiceRunning(boolean run) {
		this.runBoolean = run;
	}

	public boolean getUpdaterServiceRunning() {
		return this.runBoolean;

	}
}
