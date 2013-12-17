package com.tim.todolist3;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.tim.todolist3.NewItemFragment.OnNewItemAddedListener;

public class ToDoListActivity extends Activity implements
		OnNewItemAddedListener {
	ListView myListView;
	EditText myEditText;
	ArrayList<String> arrayList;
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// inflate the view
		setContentView(R.layout.main);

		// Get reference to the Fragments
		FragmentManager fragmentManager = getFragmentManager();
		ToDoListFragment toDoListFragment = (ToDoListFragment) fragmentManager
				.findFragmentById(R.id.ToDoListFragment);

		/*
		 * define an ArrayList of String to store each to-do list item. And bind
		 * a ListView to an ArrayList using an ArrayAdapter. I'll discuss in
		 * later version.
		 */

		/*
		 * In this version, the application will not store the data in to-do
		 * list. That is, in onCreate() method, the arrayList is the new one.
		 */
		arrayList = new ArrayList<String>();

		/*
		 * The second parameter is the resource ID for a layout file containing
		 * a TextView to use when instantiating views. Here we use the native
		 * layout(android.R.layout.simple_list_item_1) which have only one
		 * TextView. If you want to inject to the layout with more than one
		 * view, use another constructor ArrayAdapter (Context context, int
		 * resource, int textViewResourceId, T[] objects)
		 */
		arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.todolist_item, arrayList);
		/*
		 * Bind The Array Adapter to the ListView
		 */
		toDoListFragment.setListAdapter(arrayAdapter);
	}

	@Override
	public void onNewItemAdded(String newItem) {
		arrayList.add(newItem);
		arrayAdapter.notifyDataSetChanged();
	}

}
