package com.csyo.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = TabsPagerAdapter.class.getSimpleName();
	private static int TOTAL_TABS = 1;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Log.i(TAG, "creating a new instance for index " + (position+1));
		// create a new instance of fragment
		return TabFragment.newInstance(position+1);
	}

	@Override
	public int getCount() {
		return TOTAL_TABS;
	}
	
	public void setCount(int num) {
		Log.d(TAG,"TOTAL_TABS="+num);
		TabsPagerAdapter.TOTAL_TABS = num;
		notifyDataSetChanged();
	}

}
