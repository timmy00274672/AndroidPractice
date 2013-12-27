package com.tim.notepad;

import junit.framework.Test;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String FILENAME = "TEMP";
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		FragmentManager fManager = getFragmentManager();
		FragmentTransaction transaction = fManager.beginTransaction();
		EditFragment editFragment = new EditFragment();
		
		Bundle args = new Bundle();
		args.putString(FILENAME, getPreviousText());
		editFragment.setArguments(args);	
		transaction.add(R.id.frameLayoutLeft, editFragment, null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();

	}


	private String getPreviousText() {
		return getPreferences(MODE_PRIVATE).getString(FILENAME, null);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemSave:
			EditFragment editFragment = getLeftFragment();
			Toast.makeText(this, editFragment.getText(), Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.itemPref:
			Toast.makeText(this, "pref", Toast.LENGTH_SHORT).show();
			break;
		default:
			return false;
		}
		return true;
	}

	private EditFragment getLeftFragment() {
		FragmentManager fManager = getFragmentManager();
		EditFragment editFragment = (EditFragment) fManager
				.findFragmentById(R.id.frameLayoutLeft);
		return editFragment;
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		
		SharedPreferences preference = getPreferences(MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(FILENAME, getLeftFragment().getText());
		editor.commit();
	}
}
