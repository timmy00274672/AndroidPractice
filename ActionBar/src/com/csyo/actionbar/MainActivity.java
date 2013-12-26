package com.csyo.actionbar;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actions, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); 
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_help:
			// help action
			break;
		case R.id.action_location_found:
			Intent intent = new Intent(MainActivity.this, LocationFound.class);
			startActivity(intent);
			break;
		case R.id.action_refresh:
			// refresh
			break;
		case R.id.action_search:
			// search action
			break;
		}
		return true;
	}

}
