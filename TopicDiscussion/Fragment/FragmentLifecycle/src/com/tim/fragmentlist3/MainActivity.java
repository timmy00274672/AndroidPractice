package com.tim.fragmentlist3;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String MENU_ITEM_ONE = "B";
	private static final String MENU_ITEM_TWO = "removeB";

	private static final String TAG = MainActivity.class.getSimpleName();

	FragmentB mFB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, String.format("onCreate", null));

		setContentView(R.layout.main);
		setFB(searchFB());
		Toast.makeText(this, String.format("isDualPane : %B", isDualPane()),
				Toast.LENGTH_SHORT).show();
		Log.v(TAG, String.format("isDualPane : %B", isDualPane()));

		Log.v(TAG, String.format("onCreate, getFB() is null : %B",
				getFB() == null));

		if (isDualPane()) {
			if (getFB() == null || !getFB().isVisible()) {
				setFB(FragmentB.getInstance("TestString"));
				getFragmentManager().beginTransaction()
						.replace(R.id.frame1, getFB(), null).commit();
			}
		}
	}

	private void setFB(FragmentB fragmentB) {
		mFB = fragmentB;
		if (mFB != null)
			getFragmentManager().beginTransaction()
					.replace(R.id.frame1, fragmentB).commit();
	}

	private FragmentB getFB() {
		return mFB;
	}

	private FragmentB searchFB() {
		return (FragmentB) getFragmentManager().findFragmentById(R.id.frame1);
	}

	@Override
	protected void onStart() {
		Log.v(TAG, String.format("onStart", null));
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.v(TAG, String.format("onResume", null));
		super.onResume();

	}

	@Override
	protected void onPause() {
		Log.v(TAG, String.format("onPause", null));
		super.onPause();
		if (isDualPane()) {
			// removeFB();
			Log.v(TAG, String.format("FB is null : %B", getFB() == null));
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		Log.v(TAG, String.format("onStop", null));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, String.format("onDestory", null));
	}

	// @Override
	// protected void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// Log.v(TAG, String.format(
	// "onRestoreInstanceState, savedState is null : %B",
	// savedInstanceState == null));
	// }

	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// Log.v(TAG, String.format("onSaveInstanceState, saved", null));
	// }

	public boolean isDualPane() {

		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MainActivity.MENU_ITEM_ONE);
		menu.add(MainActivity.MENU_ITEM_TWO);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals(MainActivity.MENU_ITEM_ONE)) {
			// removeFB();
			// Log.v(TAG, String.format("FB is null : %B", temp == null));
			if (getFB() != null)
				Log.v(TAG, String.format("getFB() : %s, hashCode = %d", getFB()
						.toString(), getFB().hashCode()));
			Fragment tFragment = searchFB();
			if (tFragment != null)
				Log.v(TAG, String.format(
						"FB find by fragmentManager: %s, hashCode = %d",
						tFragment.toString(), tFragment.hashCode()));

			return true;
		} else if (item.getTitle().toString()
				.equals(MainActivity.MENU_ITEM_TWO)) {
			removeFB();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void removeFB() {
		if (searchFB() != null)
			getFragmentManager().beginTransaction().remove(searchFB()).commit();
	}
}
