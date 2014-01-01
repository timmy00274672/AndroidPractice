package com.tim.fragment.example;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Layout2Fragment extends Fragment {
	private static final String TAG = Layout2Fragment.class.getSimpleName();
	Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated()");
		
		super.onActivityCreated(savedInstanceState);
		button = (Button) getActivity().findViewById(R.id.button1);
		getActivity().findViewById(R.id.input);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CharSequence temp = ((EditText) getActivity().findViewById(
						R.id.input)).getText();

				Toast.makeText(getActivity(), temp, Toast.LENGTH_LONG).show();;
			}
		});
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout2, container);
	}
}
