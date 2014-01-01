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

/**
 * This fragment represents a single note. <br>
 * Use {@link EditFragment#FILENAME} as the argument to send filename data. <br>
 * Use {@link EditFragment#save()} to save current edit text. <br>
 * We retrieve the saved data from the file system in
 * {@link #onActivityCreated(Bundle)}.
 * 
 * @author csyo
 * 
 */
public class EditFragment extends Fragment {

	/**
	 * The key of the arguments that is used to get the user-input filename
	 * value from {@link MainActivity} of this fragment.
	 */
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

	/**
	 * Save the user-input content of current fragment into the file system
	 * using the filename value from {@link EditFragment#FILENAME}.
	 */
	public void save() {
		OutputStream os = null;
		OutputStreamWriter writer = null;
		try {
			os = getActivity().openFileOutput(filename, Activity.MODE_PRIVATE);
			writer = new OutputStreamWriter(os);
			writer.write(getText());
			Log.d(TAG, getText() + " saved");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				writer.close();
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
		BufferedReader buffer = null;
		try {
			openFileInput = getActivity().openFileInput(filename);
			Log.d(TAG, this.filename + " found");
			buffer = new BufferedReader(new InputStreamReader(
					openFileInput));
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
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
			}
		}
	}
}
