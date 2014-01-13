package com.tim.actionbar2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentB extends Fragment {

	private static final String ARGS_HINT = "ARGS_HINT";
	private static final String ARGS_INPUT = "ARGS_INPUT";
	private static final String ARGS_HINT_DEFAULT = "FragmentA";

	private TextView mTextView;
	private EditText mEditText;

	public static FragmentB getInstance(String defalutHint, String defalutInput) {
		FragmentB returnFragmentB = new FragmentB();
		Bundle args = new Bundle();
		args.putString(ARGS_HINT, defalutHint);
		args.putString(ARGS_INPUT, defalutInput);
		returnFragmentB.setArguments(args);
		return returnFragmentB;
	}

	public static FragmentB getInstance(Bundle args) {
		FragmentB returnFragmentB = new FragmentB();
		returnFragmentB.setArguments(args);
		return returnFragmentB;
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
		View contentView = inflater.inflate(R.layout.layout_for_fragment_b,
				container, false);
		bindView(contentView);

		return contentView;
	}

	private void bindView(View contentView) {
		mEditText = (EditText) contentView.findViewById(R.id.editTextB);
		mTextView = (TextView) contentView.findViewById(R.id.textViewB);

		Bundle args = getArguments();
		setHintText(args.getString(ARGS_HINT));
		setInputText(args.getString(ARGS_INPUT));
	}
}
