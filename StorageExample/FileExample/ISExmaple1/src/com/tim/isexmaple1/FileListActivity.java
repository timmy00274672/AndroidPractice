package com.tim.isexmaple1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FileListActivity extends ListActivity {
	private static final String TAG = FileListActivity.class.getSimpleName();
	String[] filesName = null;
	private boolean selectBooleam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filesName = fileList();
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, filesName);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.v(TAG, filesName[position] + " is selected");
		Intent returnIntent = new Intent();
		returnIntent.putExtra("FILENAME", filesName[position]);
		selectBooleam = true;
		setResult(RESULT_OK, returnIntent);
		FileListActivity.this.finish();
	}

	@Override
	protected void onPause() {
		super.onDestroy();
		if (!selectBooleam) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}
}
