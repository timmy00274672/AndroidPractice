package com.tim.compass;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		CompassView cv = (CompassView) this.findViewById(R.id.compassView);
		cv.setBearing(45);
	}

}
