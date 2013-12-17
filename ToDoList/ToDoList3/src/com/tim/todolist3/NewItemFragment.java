package com.tim.todolist3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class NewItemFragment extends Fragment {
	/**
	 * Those activities who use NewItemFragment need to implement this
	 * interface.
	 * 
	 * @author Tim
	 * 
	 */
	public interface OnNewItemAddedListener {
		public void onNewItemAdded(String newItem);
	}

	private static final String IMPLEMENT_INTERFACE = OnNewItemAddedListener.class
			.getSimpleName();

	OnNewItemAddedListener onNewItemAddedListener;

	private EditText myEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.new_item_fragment, container,
				false);
		myEditText = (EditText) view.findViewById(R.id.myEditTet);
		/*
		 * When user click the "D-pad center button" or "Enter", the text in
		 * myEditText will pass to the parent Activity's OnNewItemAddedListener
		 * , and at the same time clean the text in myEditText
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
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_ENTER) {
						// add the text to the top of to-do List
						String newItem = myEditText.getText().toString();
						onNewItemAddedListener.onNewItemAdded(newItem);
						myEditText.setText("");
						return true;
					}
				}
				return false;
			}
		});
		return view;
	}

	/**
	 * Here will check that the activity using this fragment extending
	 * {@link OnNewItemAddedListener}. If not, this method will throw a
	 * ClassCastExcetion
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onNewItemAddedListener = (OnNewItemAddedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("must implement "
					+ IMPLEMENT_INTERFACE);
		}
	}
}
