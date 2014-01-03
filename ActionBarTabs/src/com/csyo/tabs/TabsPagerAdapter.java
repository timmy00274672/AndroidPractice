package com.csyo.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private static final int MAX_TABS = 10;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		return NewTab.newInstance(index);
	}

	@Override
	public int getCount() {
		return MAX_TABS;
	}

}
