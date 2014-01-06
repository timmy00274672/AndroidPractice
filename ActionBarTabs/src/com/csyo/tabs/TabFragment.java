package com.csyo.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

	private static final String TAB_POSITION = "position";
	private static final String TAG = TabFragment.class.getSimpleName();
	private String position;
	
	static TabFragment newInstance(int num){
		TabFragment tab = new TabFragment();
		
		Bundle args = new Bundle();
		args.putInt(TAB_POSITION, num);
		tab.setArguments(args);
		
		return tab;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, position + " onCreate");
		int position = getArguments() != null ? getArguments().getInt(TAB_POSITION) : 1;
		this.position = Integer.toString(position);
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, position + " onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_untitled, container,
				false);
		TextView text = (TextView) rootView.findViewById(R.id.fragment_position);
		text.setText("Tab at position "+position);
		return rootView;
	}

	
	@Override
	public void onStart() {
		Log.i(TAG, position + " onStart");
		super.onStart();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, position + " onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, position + " onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onPause() {
		Log.i(TAG, position + " onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, position + " onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, position + " onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, position + " onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.i(TAG, position + " onDetach");
		super.onDetach();
	}
}
