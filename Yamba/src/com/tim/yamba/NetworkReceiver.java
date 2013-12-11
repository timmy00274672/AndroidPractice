package com.tim.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

	private static final String TAG = NetworkReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {

	    boolean isNetworkDown = intent.getBooleanExtra(
	        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);  // <2>
	    
	    if (isNetworkDown) {
	      Log.d(TAG, "onReceive: NOT connected, stopping UpdaterService");
	      context.stopService(new Intent(context, UpdaterService.class)); // <3>
	    } else {
	      Log.d(TAG, "onReceive: connected, starting UpdaterService");
	      context.startService(new Intent(context, UpdaterService.class)); // <4>
	    }
	}

}
