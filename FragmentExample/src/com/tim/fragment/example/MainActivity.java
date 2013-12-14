package com.tim.fragment.example;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"beginning of onCreate");
		setContentView(R.layout.activity_main);
		Log.d(TAG,"end of onCreate");
	}

}
