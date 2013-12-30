package com.tim.isexmaple1;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("DefaultLocale")
public class FileListActivity extends ListActivity {
	private interface MyComparator {

		int compare(File leftFile, File rightFile);

	}

	private static final String TAG = FileListActivity.class.getSimpleName();
	private static final int CONST = 20131230;
	static final int ID_NAME = FileListActivity.CONST;
	static final int ID_LAST_MODIFY = FileListActivity.CONST + 1;
	private static final int ID_TEST = FileListActivity.CONST + 2;
	String[] filesName = null;
	private boolean selectBooleam;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filesName = fileList();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, filesName);
		setListAdapter(adapter);
		sortByModifiedTime(false);// default sorting
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, ID_TEST, Menu.NONE, "TEST");
		SubMenu sortSubMenu = menu.addSubMenu("sort by");
		sortSubMenu.add(Menu.NONE, ID_NAME, Menu.NONE, "NAME");
		sortSubMenu.add(Menu.NONE, ID_LAST_MODIFY, Menu.NONE, "LAST_MODIFY");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ID_NAME:
			sortByName(false);
			return true;
		case ID_LAST_MODIFY:
			sortByModifiedTime(false);
			return true;
		case ID_TEST:
			test();
			return true;
		default:
			return false;
		}
	}

	@SuppressLint("DefaultLocale")
	private void test() {
		sortByName(false);
	}

	/**
	 * 
	 * @param order
	 *            false for newest top and vice versa
	 */
	private void sortByModifiedTime(final boolean order) {
		sort(order, new MyComparator() {
			
			@Override
			public int compare(File leftFile, File rightFile) {
				return (int) (rightFile.lastModified()-leftFile.lastModified());
			}
		});
	}

	private void sortByName(final boolean order){
		sort(order, new MyComparator() {
			
			@Override
			public int compare(File leftFile, File rightFile) {
				return leftFile.getName().compareTo(rightFile.getName());
			}
		});
	}
	private void sort(final boolean reverse,
			final FileListActivity.MyComparator comparator) {
		final File dirFile = getFilesDir();

		// new to old
		Arrays.sort(filesName, new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				File leftFile = new File(dirFile, lhs);
				File rightFile = new File(dirFile, rhs);
				int temp = comparator.compare(leftFile, rightFile);
				if (reverse)
					temp = -temp;
				return temp;
			}
		});

		adapter.notifyDataSetChanged();
	}
}
