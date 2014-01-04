package com.tim.dialogfragmentexample;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MyCustomDialog extends DialogFragment {
	private static final String TAG = MyCustomDialog.class.getSimpleName();
	private Button mButton;
	private EditText mEditText;
	private onSubmitListener mListener;
	private int num;

	interface onSubmitListener {
		void setOnSubmitListener(int i);
	}

//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		final Dialog dialog = new Dialog(getActivity());
//		// dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		// int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//		// dialog.getWindow().setFlags(flagFullscreen, flagFullscreen);
//		// dialog.setContentView(R.layout.custom_dialog);
//		dialog.getWindow().setBackgroundDrawable(
//				new ColorDrawable(Color.TRANSPARENT));
//		dialog.show();
//
//		return dialog;
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.custom_dialog, container, false);
		mButton = (Button) v.findViewById(R.id.button1);
		mEditText = (EditText) v.findViewById(R.id.editText1);
		mEditText.setText("");
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.setOnSubmitListener(Integer.parseInt(mEditText
						.getText().toString().trim()));
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Pick a style based on the num.
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		num = getArguments().getInt("num");

		switch (num % 8+1) {
		case 1:
			style = DialogFragment.STYLE_NO_TITLE;
			break;
		case 2:
			style = DialogFragment.STYLE_NO_FRAME;
			break;
		case 3:
			style = DialogFragment.STYLE_NO_INPUT;
			break;
		case 4:
			style = DialogFragment.STYLE_NORMAL;
			break;
		case 5:
			style = DialogFragment.STYLE_NORMAL;
			break;
		case 6:
			style = DialogFragment.STYLE_NO_TITLE;
			break;
		case 7:
			style = DialogFragment.STYLE_NO_FRAME;
			break;
		case 8:
			style = DialogFragment.STYLE_NORMAL;
			break;
		}
		switch (num % 8+1) {
		case 4:
			theme = android.R.style.Theme_Holo;
			break;
		case 5:
			theme = android.R.style.Theme_Holo_Light_Dialog;
			break;
		case 6:
			theme = android.R.style.Theme_Holo_Light;
			break;
		case 7:
			theme = android.R.style.Theme_Holo_Light_Panel;
			break;
		case 8:
			theme = android.R.style.Theme_Holo_Light;
			break;
		}
		Log.v(TAG,String.format("setStyle(%d, %d)", style, theme));
		setStyle(style, theme);
	}

	public static MyCustomDialog getInstance(onSubmitListener listener,
			int style) {
		MyCustomDialog fragment1 = new MyCustomDialog();
		fragment1.mListener = listener;
		Bundle args = new Bundle();
		args.putInt("num", style);
		fragment1.setArguments(args);
		return fragment1;
	}

}
