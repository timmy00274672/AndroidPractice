package com.csyo.tasklist;

import android.app.ListActivity;
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

import com.csyo.tasklist.TaskList.Tasks;

public class TaskListActivity extends ListActivity {

	private static final String[] PROJECTION = { Tasks._ID, Tasks.CONTENT,
			Tasks.CREATED, Tasks.ALARM, Tasks.DATE, Tasks.TIME, Tasks.ON_OFF };
	private static final int NEW = 1;
	private static final int DEL = 2;
	protected static final String TAG = TaskListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		// Set up URI
		if (intent.getData() == null) {
			intent.setData(Tasks.CONTENT_URI);
		}

		// Show the data to ListView through adapter
		Cursor cursor = getContentResolver().query(intent.getData(), PROJECTION, null, null, Tasks.DEFAULT_SORT_ORDER);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, new String[] {
						Tasks._ID, Tasks.CONTENT }, new int[] {android.R.id.text1,android.R.id.text2}, 0);
		setListAdapter(adapter);

		// bind click event for ListView
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Query the data by ID
				Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
				Cursor cursor = getContentResolver().query(uri, PROJECTION, null, null, Tasks.DEFAULT_SORT_ORDER);
				if (cursor.moveToNext()) {
					int _id = cursor.getInt(0);
					String content = cursor.getString(1);
					String created = cursor.getString(2);
					int alarm = cursor.getInt(3);
					String date = cursor.getString(4);
					String time = cursor.getString(5);
					int on_off = cursor.getInt(6);

					Bundle bundle = new Bundle();
					bundle.putInt("_id", _id);
					bundle.putString("content", content);
					bundle.putString("created", created);
					bundle.putInt("alarm", alarm);
					bundle.putString("date", date);
					bundle.putString("time", time);
					bundle.putInt("on_off", on_off);

					intent.putExtra("bundle", bundle);
					intent.setClass(TaskListActivity.this,
							TaskDetailActivity.class);
					startActivity(intent);
				} else {
					Log.e(TAG, "Cannot find data");
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, NEW, 0, "CREATE").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, DEL, 0, "DELETE").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case NEW:
			startActivity(new Intent(this, TaskDetailActivity.class));
		case DEL:
			return true;
		}
		return false;
	}

}
