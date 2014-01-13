package com.tim.actionbar2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentA extends Fragment {

	public static final String ARGS_HINT = "ARGS_HINT";
	public static final String ARGS_INPUT = "ARGS_INPUT";
	public static final String ARGS_HINT_DEFAULT = "FragmentA";

	private TextView mTextView;
	private EditText mEditText;

	public static FragmentA getInstance(String defalutHint, String defalutInput) {
		FragmentA returnFragmentA = new FragmentA();
		Bundle args = new Bundle();
		args.putString(ARGS_HINT, defalutHint);
		args.putString(ARGS_INPUT, defalutInput);
		returnFragmentA.setArguments(args);
		return returnFragmentA;
	}

	public static FragmentA getInstance(Bundle args) {
		FragmentA returnFragmentA = new FragmentA();
		returnFragmentA.setArguments(args);
		return returnFragmentA;
	}

	public void setHintText(String string) {
		mTextView.setText(string != null ? string : ARGS_HINT_DEFAULT);
	}

	public void setInputText(String string) {
		mEditText.setText(string != null ? string : "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.layout_for_fragment_a,
				container, false);
		bindView(contentView);

		return contentView;
	}

	private void bindView(View contentView) {
		mEditText = (EditText) contentView.findViewById(R.id.editTextA);
		mTextView = (TextView) contentView.findViewById(R.id.textViewA);

		Bundle args = getArguments();

		setHintText(args.getString(ARGS_HINT));
		setInputText(args.getString(ARGS_INPUT));
	}
}
