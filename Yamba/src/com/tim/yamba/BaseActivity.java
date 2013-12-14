package com.tim.yamba;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Common features shared by {@link TimelineActivity} and {@link StatusActivity}
 * .
 * 
 * @author Tim
 * 
 */
public class BaseActivity extends Activity {

	private static final String TAG = BaseActivity.class.getSimpleName();
	/**
	 * share by sub-classes.
	 */
	YambaApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (YambaApplication) getApplication();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Key points:
	 * <ul>
	 * <li>Use intent to call other activity, with {@link #StatusActivity()}</li>
	 * <li>here we use {@link #startActivity}, {@link #startService} ,
	 * {@link #stopService} to control service and connecting other activities
	 * </ul>
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@SuppressLint("ShowToast")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.itemToggle:
			if (application.isUpdaterServiceRunning()) {
				stopService(new Intent(this,UpdaterService.class));
			} else {
				startService(new Intent(this,UpdaterService.class));
			}
			break;
		case R.id.itemPurge:
			application.getStatusData().delete();
			Intent intent = new Intent(YambaApplication.UPDATE_PERMISSION);
			sendBroadcast(intent,YambaApplication.UPDATE_PERMISSION);
			Toast.makeText(this, R.string.msgAllDataPurge, Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.itemStatus:
			startActivity(new Intent(this, StatusActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.itemTimeline:
			startActivity(new Intent(this, TimelineActivity.class).addFlags(
					Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(
					Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.itemDebug:
			Toast.makeText(this,
					Boolean.toString(application.isUpdaterServiceRunning()),
					Toast.LENGTH_LONG).show();
		}
		return true;

	}

//	@Override
//	public boolean onMenuOpened(int featureId, Menu menu) {
////		Log.d(TAG,menu.toString());
////		MenuItem toggleItem = menu.findItem(R.id.itemToggle);
////		if (application.isUpdaterServiceRunning()) {
////			toggleItem.setTitle(R.string.titleServiceStop);
////			toggleItem.setIcon(android.R.drawable.ic_media_pause);
////		} else {
////			toggleItem.setTitle(R.string.titleServiceStart);
////			toggleItem.setIcon(android.R.drawable.ic_media_play);
////		}
//
//		return true;
//	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.d(TAG,menu.toString());
		MenuItem toggleItem = menu.findItem(R.id.itemToggle);
		if (application.isUpdaterServiceRunning()) {
			toggleItem.setTitle(R.string.titleServiceStop);
			toggleItem.setIcon(android.R.drawable.ic_media_pause);
		} else {
			toggleItem.setTitle(R.string.titleServiceStart);
			toggleItem.setIcon(android.R.drawable.ic_media_play);
		}
		return true;
	}
}
