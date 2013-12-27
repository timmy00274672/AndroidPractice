package com.tim.notepad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditFragment extends Fragment {

	public static final String FILENAME = "FILENAME";
	private static final String TAG = EditFragment.class.getSimpleName();
	private String filename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		filename = getArguments().getString(FILENAME, "untitled");
		Log.d(TAG, this.filename);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_layout, container, false);
	}

	public void save() {
		OutputStream os = null;
		OutputStreamWriter writer = null;
		try {

			os = getActivity().openFileOutput(filename, Activity.MODE_PRIVATE);
			writer = new OutputStreamWriter(os);
			writer.write(getText());
			// os.write(97);
			Log.d(TAG, getText() + " saved");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				writer.close();
				os.close();
			} catch (IOException e) {
			}
		}
	}

	public String getText() {
		return getEditText().getText().toString();
	}

	public void setText(String text) {
		getEditText().setText(text);
	}

	private EditText getEditText() {
		return (EditText) getView().findViewById(R.id.editText);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FileInputStream openFileInput = null;
		try {
			openFileInput = getActivity().openFileInput(filename);
			Log.d(TAG, this.filename + " found");
			InputStreamReader reader = new InputStreamReader(openFileInput);
			BufferedReader buffer = new BufferedReader(reader);
			String tmp = null;
			StringBuffer stringbuffer = new StringBuffer();
			while ((tmp = buffer.readLine()) != null) {
				stringbuffer.append(tmp);
			}
			Log.d(TAG, stringbuffer.toString());
			setText(stringbuffer.toString());
		} catch (FileNotFoundException e) {
			Log.d(TAG, " file not found");
		} catch (IOException e) {
		}
	}
}
