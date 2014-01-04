package com.csyo.tabs;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private ViewPager viewPager;
	private ActionBar actionBar;
	private TabsPagerAdapter mAdapter;
	private ArrayList<String> tabs;
	private int tabCount = 0;
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	private TabListener tabListener = new TabListener() {

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			viewPager.setCurrentItem(tab.getPosition());
			Log.i(TAG, tab.getPosition() + " selected");
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(pageChangeListener);

		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tabs = new ArrayList<String>();
		
		if (savedInstanceState != null) {
			Log.d(TAG, "loading... " + tabs.toString());
			tabs = savedInstanceState.getStringArrayList("TABS");
			Log.d(TAG, "loaded " + tabs.toString());
		}
		if (getTabCount() == 0) {
			Log.i(TAG,"creat #0 tab");
			addTab();
		} else {
			Log.d(TAG, "TabCount=" + getTabCount());
			for (String tabName : tabs) {
				addTab(tabName);
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList("TABS", tabs);
		Log.d(TAG, "saving " + tabs.toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addTab:
			if (getTabCount() < mAdapter.getCount()) {
				addTab();
			} else {
				Toast.makeText(this, "Too many tabs", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.removeTab:
			Tab tab = actionBar.getSelectedTab();
			tabs.remove(tab.getPosition());
			actionBar.removeTab(tab);
			if (getTabCount() == 0) {
				addTab();
			}
			break;
		}
		Log.i(TAG,"done "+tabs.toString());
		return true;
		
	}

	private void addTab() {
		tabCount++;
		String tabName = "Tab_" + tabCount;
		actionBar.addTab(actionBar.newTab().setText(tabName)
				.setTabListener(tabListener));
		tabs.add(tabName);
	}

	private void addTab(String tabName) {
		actionBar.addTab(actionBar.newTab().setText(tabName)
				.setTabListener(tabListener));
	}

	public int getTabCount() {
		return tabs.size();
	}

}
