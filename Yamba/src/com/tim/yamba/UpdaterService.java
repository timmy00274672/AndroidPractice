package com.tim.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	private Updater updater;
	private YambaApplication application;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(UpdaterService.TAG, "in onStartCommand");
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
			Log.d(TAG,
					"in run "
							+ Boolean.toString(application
									.isUpdaterServiceRunning()));
			while (application.isUpdaterServiceRunning()) {
				try {
					application.fetchStatusUpdates();
					Intent intent = new Intent(YambaApplication.UPDATE_PERMISSION);
					sendBroadcast(intent,YambaApplication.UPDATE_PERMISSION);
					Log.d(TAG,"after send broadcast");
					sleep(DELAY);
				} catch (InterruptedException e) {
					// e.printStackTrace();
					// UpdaterService.this.onDestroy();
				}
			}
			Log.d(TAG,"ready to leave run()");
		}
	}


}
