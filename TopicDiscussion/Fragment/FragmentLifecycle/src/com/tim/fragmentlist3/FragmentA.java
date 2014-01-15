package com.tim.fragmentlist3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentA extends Fragment {

	private static final String TAG = FragmentA.class.getSimpleName();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, String.format("onAtach, this Fragment is %s, hashCode = %d",
				this.toString(), this.hashCode()));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, String.format("%s", "onCreate"));
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
		EditText lEditText = new EditText(getActivity());
		lEditText.setText("A");
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
