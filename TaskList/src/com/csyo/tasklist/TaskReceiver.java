package com.csyo.tasklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TaskReceiver extends BroadcastReceiver {

	private static final String TAG = TaskReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive()");
		Toast.makeText(context, "alarm!", Toast.LENGTH_SHORT).show();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, AlarmActivity.class);
		context.startActivity(intent);
	}
}
