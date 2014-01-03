package com.tim.mp3player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {

	private static final String TAG = PlayerService.class.getSimpleName();
	private MediaPlayer mp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand()");
		mp = MediaPlayer.create(this, R.raw.test);
		mp.start();
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				PlayerService.this.stopSelf();
			}
		});
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy()");
		super.onDestroy();
		mp.stop();
	}

}
