This is note for `SearchView`.

I use below code to dynamically add a `MenuItem` and bind a view to it. And show it in the action bar.

```java
	searchView = new SearchView(this);
	MenuItem search = menu.add(Menu.NONE, ID_SEARCH, Menu.NONE, "search");
	search.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	search.setActionView(searchView);
	searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

		@Override
		public boolean onQueryTextSubmit(String query) {
			Toast.makeText(SearchViewActivity.this, query,
					Toast.LENGTH_LONG).show();
			return true;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			if (newText.equals(""))
				searchTextView.setText("default");
			else
				searchTextView.setText(newText);

			return true;
		}

	});
```

Note that the listener's callback functions return `true` if it cope with the event already.

In `onCreateOptionsMenu`:

```java
	MenuItem clock = menu.add(Menu.NONE, ID_CLOCK, Menu.NONE, "clock");
	View clockView = getLayoutInflater()
			.inflate(R.layout.test_layout, null);
	clock.setActionView(clockView);
	clock.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	clock.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Log.v(TAG,"clock's setOnMenuItemClickListener");
			return false;
		}
		
	});
```

And in onOptionsItemSelected

```java
	Log.v(TAG,"onOptionsItemSelected "+ item.getTitle());
	return false;
```

If we select the clock item, the logcat:

```
	01-04 06:12:52.446: V/SearchViewActivity(3501): clock's setOnMenuItemClickListener
	01-04 06:12:52.446: V/SearchViewActivity(3501): onOptionsItemSelected clock
```

It shows two things:

- The priority
- The return value will decide if call next handler.