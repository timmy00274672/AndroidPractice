package com.tim.notepadwithcp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	public static final String[] TITLES = new String[] { "class", "subclass",
			"ps" };
	ListView mRecordsListView;

	private ListAdapter adapter;
	private Button mNextButton, mSaveButton, mCancelButton;
	private DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbHelper = new DBHelper(getApplication());

		// bind view
		mNextButton = (Button) findViewById(R.id.nextButton);
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		mSaveButton = (Button) findViewById(R.id.saveButton);
		mRecordsListView = (ListView) findViewById(R.id.recordsList);

		adapter = new MyListAdapter();
		mRecordsListView.setAdapter(adapter);
		/*
		 * I quit using list :
		 * 
		 * view mRecordsListView = (ListView) findViewById(R.id.recordsList);
		 * adapter = new ArrayAdapter<String>(this, R.layout.row,
		 * R.id.rowTittleTextView, ROWS); mRecordsListView.setAdapter(adapter);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("test");
		menu.add("init");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("test")) {
			test();
			return true;
		} else if (item.getTitle().equals("init")) {
			return true;
		}

		return false;
	}

	/*
	 * I quit using list:
	 * 
	 * private void testList() { Toast.makeText( this,
	 * String.format("Child View # = %d", mRecordsListView.getChildCount()),
	 * Toast.LENGTH_SHORT) .show(); for (int index = 0; index <
	 * mRecordsListView.getChildCount(); index++) { View childView =
	 * mRecordsListView.getChildAt(index); String tittle = ((TextView) childView
	 * .findViewById(R.id.rowTittleTextView)).getText().toString(); String value
	 * = ((EditText) childView
	 * .findViewById(R.id.rowValueEditText)).getText().toString();
	 * 
	 * Log.v(TAG, String.format("Title(%s) = %s", tittle, value));
	 * 
	 * }
	 * 
	 * }
	 */

	private void test() {
		DBHelper dbHelper = new DBHelper(getApplication());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.C1_TABLE_NAME, null, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			Log.v(TAG,
					String.format("C1[%s] = %s", cursor.getInt(0),
							cursor.getString(1)));
		}
		db.close();
	}

	public class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 1;
		}

		/**
		 * @return the position.
		 */
		@Override
		public Object getItem(int position) {
			return position;
		}

		/**
		 * @return the position.
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = MainActivity.this
					.getLayoutInflater();
			switch (position) {
			case 0:
				// class
				View classView = layoutInflater.inflate(R.layout.row, parent,
						false);
				((TextView) classView.findViewById(R.id.rowTittleTextView))
						.setText(TITLES[0]);
				FrameLayout frameLayout = (FrameLayout) classView
						.findViewById(R.id.frameInRow);
				Spinner mClassValueSpinner = new Spinner(getApplication(),
						Spinner.MODE_DROPDOWN);
				// EditText mClassValueView = new EditText(getApplication());
				frameLayout.addView(mClassValueSpinner);

				Cursor cursor = dbHelper.getReadableDatabase().query(
						DBHelper.C1_TABLE_NAME, null, null, null, null, null,
						null);

				//
				// // Cursor cursor = null;
				SpinnerAdapter mSpinnerAdapter = new SimpleCursorAdapter(
						getApplication(), android.R.layout.simple_list_item_1,
						cursor, new String[] { DBHelper.C1_NAME },
						new int[] { android.R.id.text1 },
						CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
				mClassValueSpinner.setAdapter(mSpinnerAdapter);

				mClassValueSpinner
						.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								String temp = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
								Log.v(TAG, String.format("V(%d)[%s] selected", position,temp));
								
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								// TODO Auto-generated method stub
								
							}

						});
				return classView;

			}
			return parent;

		}
	}
}
