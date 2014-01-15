package com.tim.fragmentlist3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentB extends Fragment {

	private static final String TAG = FragmentB.class.getSimpleName();

	public final static String K_FILENAME = "K_FILENAME";

	private static final String FAIL_STATE_NOTIFICATION = "FAIL_STATE_NOTIFICATION";
	private String filename = "not success";

	private EditText lEditText;

	public static FragmentB getInstance(String filename) {
		FragmentB lFragmentB = new FragmentB();
		Bundle args = new Bundle();
		args.putString(K_FILENAME, filename);
		lFragmentB.setArguments(args);
		return lFragmentB;
	}

	private String getFileName() {
		if (lEditText == null) {
			return FAIL_STATE_NOTIFICATION;
		}
		filename = lEditText.getText().toString();
		if (filename == null)
			setFilename(null);
		return filename;
	}

	private void setFilename(String filename) {
		this.filename = filename == null ? "" : filename;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, String.format("onAtach, this Fragment is %s, hashCode = %d",
				this.toString(), this.hashCode()));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, String.format("onCreate", null));

		if (savedInstanceState != null) {
			Log.v(TAG, String.format("onCreate, savedInstanceState is %s",
					savedInstanceState.toString()));
		}

		if (getArguments() != null) {

			Log.v(TAG, String.format("onCreate, getArguments() is %s ",
					getArguments().toString()));
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(K_FILENAME, getFileName());
		Log.v(TAG, String.format(
				"in onSaveInstanceState, save filename(%s) already",
				getFileName()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, String.format(
				"onCreateView(), savedInstanceState is null: %B",
				savedInstanceState == null));
		if (savedInstanceState != null)
			Log.v(TAG, String.format("onCreateView, savedInstanceState is %s",
					savedInstanceState.toString()));
		lEditText = new EditText(getActivity());
		lEditText.setText(getFileName());
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lEditText.setLayoutParams(params);
		return lEditText;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG, String.format("%s", "onStart"));
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, String.format("%s", "onResume"));
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG, String.format("%s", "onPause"));
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG, String.format("%s", "onStop"));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.v(TAG, String.format("%s", "onDestoryView"));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, String.format("%s", "onDestory"));
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.v(TAG, String.format("%s", "onDetach"));
	}
}
