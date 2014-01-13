package com.tim.actionbar2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// actionBar.setDisplayShowTitleEnabled(false);

		Bundle args = new Bundle();
		args.putString(FragmentA.ARGS_HINT, "YEShint");
		actionBar.addTab(actionBar
				.newTab()
				.setText("V1")
				.setTabListener(
						new MyTabListener<FragmentA>(this, "A",
								FragmentA.class, args)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("B")
				.setTabListener(
						new MyTabListener<FragmentB>(this, "B",
								FragmentB.class, args)));
	}

}
