package com.csyo.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch(index){
//		case 0:
//			return new TabOne();
//		case 1:
//			return new TabTwo();
//		case 2:
//			return new TabThree();
		default:
			return new TabNew();
 		}
	}

	@Override
	public int getCount() {
		return 7;
	}

}
