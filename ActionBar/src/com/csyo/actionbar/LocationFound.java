package com.csyo.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class LocationFound extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_found);
		
		ActionBar actionBar = getActionBar();
		// Up Navigation
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

}
