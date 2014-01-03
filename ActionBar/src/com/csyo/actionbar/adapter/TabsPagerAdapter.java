package com.csyo.actionbar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.csyo.actionbar.TabOne;
import com.csyo.actionbar.TabThree;
import com.csyo.actionbar.TabTwo;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		
		switch(index){
		case 0:
			return new TabOne();
		case 1:
			return new TabTwo();
		case 2:
			return new TabThree();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
