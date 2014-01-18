package com.csyo.tasklist;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.csyo.tasklist.TaskList.Tasks;

public class TaskListActivity extends ListActivity {

	protected static final String TAG = TaskListActivity.class.getSimpleName();
	private static final String[] PROJECTION = { Tasks._ID, Tasks.CONTENT,
			Tasks.CREATED, Tasks.ALARM, Tasks.DATE, Tasks.TIME, Tasks.ON_OFF };
	private static final int NEW = 1;
	private static final int DEL = 2;
	private BroadcastReceiver br;
	private AlarmManager am;
	private PendingIntent pending;
	private Intent mIntent;
	private Cursor mCursor;
	private SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		updateTaskList();

		// bind click event for ListView
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Query the data by ID
				Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
				Cursor cursor = getContentResolver().query(uri, PROJECTION,
						null, null, Tasks.DEFAULT_SORT_ORDER);
				if (cursor.moveToNext()) {
					int _id = cursor.getInt(0);
					String content = cursor.getString(1);
					String created = cursor.getString(2);
					int alarm = cursor.getInt(3);
					String date = cursor.getString(4);
					String time = cursor.getString(5);
					int on_off = cursor.getInt(6);

					Bundle bundle = new Bundle();
					bundle.putInt(Tasks._ID, _id);
					bundle.putString(Tasks.CONTENT, content);
					bundle.putString(Tasks.CREATED, created);
					bundle.putString(Tasks.DATE, date);
					bundle.putString(Tasks.TIME, time);
					bundle.putInt(Tasks.ALARM, alarm);
					bundle.putInt(Tasks.ON_OFF, on_off);

					mIntent.putExtra(TaskDetailActivity.TASK_BUNDLE, bundle);
					mIntent.setClass(TaskListActivity.this,
							TaskDetailActivity.class);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(mIntent);
				} else {
					Log.e(TAG, "Cannot find data");
				}
			}
		});

		// create ListView-specific touch listener
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listView,
				new SwipeDismissListViewTouchListener.DismissCallbacks() {

					@Override
					public void onDismiss(ListView listView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							long item = listView.getItemIdAtPosition(position);
							Uri uri = ContentUris.withAppendedId(
									Tasks.CONTENT_URI, item);
							getContentResolver().delete(uri, null, null);
							Toast.makeText(TaskListActivity.this, "uri="+ uri,
									Toast.LENGTH_SHORT).show();
						}
						updateTaskList();
					}

					@Override
					public boolean canDismiss(int position) {
						return true;
					}
				});
		listView.setOnTouchListener(touchListener);
		listView.setOnScrollListener(touchListener.makeScrollListener());
	}

	/**
	 * Get intent and retrieve URI, and query all data with the URI. Then set up
	 * the adapter to show on the listView
	 * 
	 */
	private void updateTaskList() {
		mIntent = getIntent();
		if (mIntent.getData() == null) {
			mIntent.setData(Tasks.CONTENT_URI);
		}

		mCursor = getContentResolver().query(mIntent.getData(), PROJECTION,
				null, null, Tasks.DEFAULT_SORT_ORDER);
		mAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, mCursor, new String[] {
						Tasks._ID, Tasks.CONTENT }, new int[] {
						android.R.id.text1, android.R.id.text2 }, 0);
		setListAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, NEW, 0, "+").setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, DEL, 0, "-ALL").setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case NEW:
			startActivity(new Intent(this, TaskDetailActivity.class));
			return true;
		case DEL:
			getContentResolver().delete(Tasks.CONTENT_URI, null, null);
			updateTaskList();
			return true;
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateTaskList();
	}

	@Override
	protected void onDestroy() {
		if (am != null)
			am.cancel(pending);
		if (br != null)
			unregisterReceiver(br);
		super.onDestroy();
	}
}
