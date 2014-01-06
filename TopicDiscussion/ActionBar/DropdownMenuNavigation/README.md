This is note for `DropdownMenuNavigation`.

When you wnat to add a spinner in action bar, you need to 

1. Change the mode to `NAVIGATION_MODE_LIST`

	```java

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	```

2. `setListNavigationCallbacks`


	```java

		final String[] dropdownValues = { "1", "2", "3" };

		// Specify a SpinnerAdapter to populate the dropdown list.

		// use getActionBar().getThemedContext() to ensure
		// that the text color is always appropriate for the action bar
		// background rather than the activity background.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actionBar.getThemedContext(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				dropdownValues);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(adapter, this);

	```

3. override `onNavigationItemSelected` method

	```java

	
		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			// When the given dropdown item is selected, show its contents in the
			// container view.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, itemPosition + 1);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
			return true;
		}

	```
	

