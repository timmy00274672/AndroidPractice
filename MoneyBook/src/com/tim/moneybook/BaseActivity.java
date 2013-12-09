package com.tim.moneybook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	private static final String TAG = BaseActivity.class.getName();

	MoneyApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (MoneyApplication) getApplication();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemAdd:
			Log.d(TAG,"you select itemAdd");
			startActivity(new Intent(this, AddItemActivity.class));
			break;
		case R.id.itemShow:
			Log.d(TAG,"you select itemShow");
			startActivity(new Intent(this, ShowdataActivity.class));
			break;

		}
		return true;
	}
}
