package com.tim.fragment.example;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "beginning of onCreate");
		setContentView(R.layout.layout2);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		Log.d(TAG, "end of onCreate");

	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestory()");
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();
		ArticleFragment articleFragment = (ArticleFragment) fragmentManager
				.findFragmentById(R.id.framelayout1);
		if (articleFragment == null) {
			Log.d(TAG, "there is no articleFragment");
			articleFragment = new ArticleFragment();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.add(R.id.framelayout1, articleFragment, "test");
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.commit();
		}
	}
}
