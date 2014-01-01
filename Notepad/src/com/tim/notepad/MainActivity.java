package com.tim.notepad;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Provide a text area to enter the filename to create/open the note.
 * Either way, when clicked the submit button, a fragment will be created
 * to show the note.
 * 
 * @author csyo
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private EditText filenameEditText;
	private Button buttonSubmit;
	private OnClickListener submitButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String filename = getFilename();

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			EditFragment fragment = new EditFragment();

			Bundle args = new Bundle();
			args.putString(EditFragment.FILENAME, filename);
			fragment.setArguments(args);
			Log.d(TAG, args.getString(EditFragment.FILENAME, "null"));
			
			fragmentTransaction.replace(R.id.frameLayoutLeft, fragment, null);
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			// fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}

	};

	private String getFilename() {
		return filenameEditText.getText().toString();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		filenameEditText = (EditText) findViewById(R.id.fileName);
		buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

		buttonSubmit.setOnClickListener(submitButtonListener);
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
			editFragment.save();
			break;
		case R.id.itemPref:
			Toast.makeText(this, "pref", Toast.LENGTH_SHORT).show();
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return the visible {@link EditFragment} fragment.
	 */
	private EditFragment getLeftFragment() {
		FragmentManager fManager = getFragmentManager();
		EditFragment editFragment = (EditFragment) fManager
				.findFragmentById(R.id.frameLayoutLeft);
		return editFragment;
	}
}
