package com.csyo.tabs;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
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
	private ArrayList<String> tabs = null;
	// private String[] tabs = { "Tab 1", "Tab 2" };
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
			Log.d(TAG, tab.getPosition() + " selected");
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	};
//	private int tabCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		if(savedInstanceState != null)
			tabs = savedInstanceState.getStringArrayList("TABS");
		if (tabs == null)
			tabs = new ArrayList<String>();

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		if (getTabCount() == 0) {
			tabs.add("Tab 1");
		}

		for (String tabName : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tabName)
					.setTabListener(tabListener));
		}

//		setTabCount(actionBar.getTabCount());
		viewPager.setOnPageChangeListener(pageChangeListener);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList("TABS", tabs);
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
				String tabName = "New Tab";
				actionBar.addTab(actionBar.newTab().setText(tabName)
						.setTabListener(tabListener));
				tabs.add(tabName);
			} else {
				Toast.makeText(this, "Too many tabs", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.removeTab:
			Tab tab = actionBar.getSelectedTab();
			tabs.remove(tab.getPosition());
			actionBar.removeTab(tab);
			break;
		}
//		setTabCount(actionBar.getTabCount());
		return true;
	}

	public int getTabCount() {
		return tabs.size();
	}

//	public void setTabCount(int tabCount) {
//		this.tabCount = tabCount;
//		Log.d(TAG, "TabCount=" + tabCount);
//	}
}
