package com.tim.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.tim.todolist1.R;

public class ToDoListActivity extends Activity {
	private static final String INLINE_ON_KEY_LISTENER_OBJECT = "inline OnKeyListener object";
	ListView myListView;
	EditText myEditText;
	ArrayList<String> arrayList;
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// inflate the view
		setContentView(R.layout.main);

		// get the reference to UI widgets
		myListView = (ListView) findViewById(R.id.myListView);
		myEditText = (EditText) findViewById(R.id.myEditTet);

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
				android.R.layout.simple_list_item_1, arrayList);
		/*
		 * Bind The Array Adapter to the ListView
		 */
		myListView.setAdapter(arrayAdapter);

		/*
		 * When user click the "D-pad center button" or "Enter", the text in
		 * myEditText will store in the arrayList and notify the arrayAdapter to
		 * upgrade the view which is bound to that arrayAdapter, and at the same
		 * time clean the text in myEditText
		 */
		myEditText.setOnKeyListener(new OnKeyListener() {

			/*
			 * return True if the listener has consumed the event, false
			 * otherwise.
			 */
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// KeyEvent.ACTION_DOWN : the key has been pressed down.
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					Log.d(ToDoListActivity.INLINE_ON_KEY_LISTENER_OBJECT,
							String.format("key: %d", event.getAction()));
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_ENTER) {
						Log.d(ToDoListActivity.INLINE_ON_KEY_LISTENER_OBJECT,
								"before add to the arrayList");
						// add the text to the top of to-do List
						arrayList.add(0, myEditText.getText().toString());
						Log.d(INLINE_ON_KEY_LISTENER_OBJECT, myEditText
								.getText().toString());
						arrayAdapter.notifyDataSetChanged();
						myEditText.setText("");
						return true;
					}
				}
				return false;
			}
		});

	}
}
