package com.csyo.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = TabsPagerAdapter.class.getSimpleName();
	private static final int MAX_TABS = 5;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Log.i(TAG, "creating a new tab instance for index " + position);
		// create a new instance for NewTab fragment
		return NewTab.newInstance(position);
	}

	@Override
	public int getCount() {
		return MAX_TABS;
	}

}
