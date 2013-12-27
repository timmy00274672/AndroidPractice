package com.tim.notepad;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String FILENAME = "TEMP";
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		FragmentManager fManager = getFragmentManager();
		FragmentTransaction transaction = fManager.beginTransaction();
		EditFragment editFragment = new EditFragment();
		transaction.add(R.id.frameLayoutLeft, editFragment, null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
		
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
	
}
