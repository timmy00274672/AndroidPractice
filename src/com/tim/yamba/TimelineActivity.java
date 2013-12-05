package com.tim.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

/**
 * It's activity(window) to show the status
 * 
 * @author Tim
 * 
 */
public class TimelineActivity extends Activity {
	YambaApplication application;
	private ListView listView;
	private SQLiteDatabase db;
	private static String[] FROM = { StatusData.C_CREATED_AT,
			StatusData.C_USER, StatusData.C_TEXT };
	private static int[] TO = { R.id.textCreatedAt, R.id.textUser,
			R.id.textText };
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		listView = (ListView) findViewById(R.id.listTimeline);
		application = (YambaApplication) getApplication();
		db = application.getStatusData().getDbHelper().getReadableDatabase();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	/**
	 * get data from the databas and set up the adatper
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		Cursor cursor = db.query(StatusData.TABLE, null, null, null, null,
				null, StatusData.C_CREATED_AT + " DESC");
		startManagingCursor(cursor);
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
		adapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				if (view.getId() != R.id.textCreatedAt)
					return false;
				else {
					long time = cursor.getLong(cursor
							.getColumnIndex(StatusData.C_CREATED_AT));
					TextView textView = (TextView) view
							.findViewById(R.id.textCreatedAt);
					textView.setText(DateUtils.getRelativeTimeSpanString(time));
					return true;
				}
			}
		});
		listView.setAdapter(adapter);
	}

}
