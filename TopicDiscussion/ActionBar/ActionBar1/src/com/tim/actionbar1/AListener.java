package com.tim.actionbar1;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.util.Log;

public class AListener implements ActionBar.TabListener {
	private final String TAG = AListener.class.getName();
	AFragment aFragment;
	
	public AListener() {
		aFragment = new AFragment();
	}

	public AListener(AFragment aFragment){
		this.aFragment = aFragment;
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.v(TAG, "onTabSelected");
		ft.add(android.R.id.content, aFragment, null);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.v(TAG, "onTabUnselected");
		ft.remove(aFragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.v(TAG, "onTabReselected");
	}
}