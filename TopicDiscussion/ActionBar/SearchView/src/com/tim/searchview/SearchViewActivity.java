package com.tim.searchview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchViewActivity extends Activity {

	private static final int ID_SEARCH = 201414;
	private static final int ID_CLOCK = 201415;
	protected static final String TAG = SearchViewActivity.class.getSimpleName();
	TextView searchTextView;
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		searchTextView = new TextView(this);
		searchTextView.setText("default");
		setContentView(searchTextView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.v(TAG,"onOptionsItemSelected "+ item.getTitle());
		return false;
	}
}
