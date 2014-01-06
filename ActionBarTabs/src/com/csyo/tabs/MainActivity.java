package com.csyo.tabs;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private ViewPager viewPager;
	private ActionBar actionBar;
	private TabsPagerAdapter mAdapter;
	private ArrayList<String> tabs;
	private int tabCount = 0;
	private SimpleOnPageChangeListener pageChangeListener = new SimpleOnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
		}

	};
	private TabListener tabListener = new TabListener() {

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			viewPager.setCurrentItem(tab.getPosition());
			Log.i(TAG, tab.getPosition() + 1 + " selected");
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
			tabs = savedInstanceState.getStringArrayList("TABS");
			tabCount = savedInstanceState.getInt("tabCount");
		}
		if (getTabCount() == 0) {
			Log.i(TAG, "create 1st tab");
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
		outState.putInt("tabCount", tabCount);
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
			addTab();
			break;
		case R.id.removeTab:
			removeTab();
			break;
		}
		Log.i(TAG, "done " + tabs.toString());
		return true;

	}

	private void removeTab() {
		Tab tab = actionBar.getSelectedTab();
		tabs.remove(tab.getPosition());
		actionBar.removeTab(tab);
		mAdapter.setCount(getTabCount());
		if (getTabCount() == 0) {
			addTab();
		}
	}

	private void addTab() {
		tabCount++;
		String tabName = "Tab_" + tabCount;
		actionBar.addTab(actionBar.newTab().setText(tabName)
				.setTabListener(tabListener));
		tabs.add(tabName);
		mAdapter.setCount(getTabCount());
	}

	private void addTab(String tabName) {
		actionBar.addTab(actionBar.newTab().setText(tabName)
				.setTabListener(tabListener));
		mAdapter.setCount(getTabCount());
	}

	public int getTabCount() {
		return tabs.size();
	}

}
