package com.tim.notepad;

import android.R.string;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_layout, container, false);
	}

	public String getText() {

		return getEditText().getText().toString();
	}

	public void setText(String text){
		getEditText().setText(text);
	}
	
	private EditText getEditText() {
		return (EditText) getView().findViewById(R.id.editText);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getArguments() != null) {
			
			setText(getArguments().getString(MainActivity.FILENAME));
		}
	}
}
