This is the readme file for Action Bar Tabs demo

## Essential: Using Action Bar Tab navigation

- Combine [`ActionBar.Tab`](http://developer.android.com/reference/android/app/ActionBar.Tab.html)
and [`Fragment`](http://developer.android.com/reference/android/app/Fragment.html) to create tab views

- As for `MainActivity` to hold them, we needs to
	1. extend `android.support.v4.app.FragmentActivity`
	2. use `android.support.v4.view.ViewPager` in layout file

		```xml
		<android.support.v4.view.ViewPager
			android:id="@+id/pager"
			android:layout_width="match_parent"
			android:layout_height="match_parent">    
		</android.support.v4.view.ViewPager>
		```

	3. set the `ViewPager` with the `TabsPagerAdapter` that extends [`FragmentPagerAdapter`](http://developer.android.com/reference/android/support/v4/app/FragmentPagerAdapter.html), and add a [`ViewPager.OnPageChangeListener`](http://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener.html) to make the tab selected on swiping
		
		```java
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(pageChangeListener);
		```

	4. set Action Bar in Tab Navtigation Mode and add tabs, which need to set up [`ActionBar.TabListener`](http://developer.android.com/reference/android/app/ActionBar.TabListener.html) to make the view changed automatically on selecting tab

		```java
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			...
		actionBar.addTab(actionBar.newTab().setText(tabName)
				.setTabListener(tabListener));
		```

	5. `TabsPagerAdapter` provides fragment views to tabs

		```java
		public class TabsPagerAdapter extends FragmentPagerAdapter {
			private static final int MAX_TABS = 10;

			public TabsPagerAdapter(FragmentManager fm) {
				super(fm);
			}

			@Override
			public Fragment getItem(int position) {
				// create a new instance for NewTab fragment
				return NewTab.newInstance(position);
			}

			@Override
			public int getCount() {
				return MAX_TABS;
			}

		}
		```

## Additional: Preserve views upon changing orientation