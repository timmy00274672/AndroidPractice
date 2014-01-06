package com.tim.actionbar1;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ActionBar1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.setDisplayShowTitleEnabled(false);
		AFragment aFragment = new AFragment();
		actionBar.addTab(actionBar.newTab().setText("V1")
				.setTabListener(aFragment.new Listener()));
		BFragment bFragment = new BFragment();
		actionBar.addTab(actionBar.newTab().setText("V2")
				.setTabListener(bFragment.new Listener()));
	}

}
