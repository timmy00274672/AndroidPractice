
## Activity
If you want to use tab, you should:

1. set navigation mode as `ActionBar.NAVIGATION_MODE_TABS`

	```java

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

	```

2. add a tab with `ActionBar.TabListener`

	```java

		actionBar.addTab(actionBar.newTab().setText("V1")
				.setTabListener(new AListener(aFragment)));
	```

## ActionBar.TabListener

With three callback function

- public void onTabUnselected(Tab tab, FragmentTransaction ft)
- public void onTabSelected(Tab tab, FragmentTransaction ft) 
- public void onTabReselected(Tab tab, FragmentTransaction ft)

Note that you should not commit the `FragmentTransaction` in these functions.

## Furture Explore

In this example, you can observe the two tabs' behavior when you change the orientation, and close the app. 

Observe that when you rotate the screen, the tab2 may contain the previous tab1's content.

I think the reason is that I use `add`. As I use `replace` instead of `add`, the problem is gone. But why? 

```java

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.v(TAG, "onTabSelected");
		ft.add(android.R.id.content, aFragment, null);
	}

```