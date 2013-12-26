package com.tim.fragment.example;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity using this fragment must implement the inner interface -
 * {@link OnInputChnagedListener}. Otherwise, {@link #onAttach(Activity)} will
 * throw a {@link ClassCastException}.
 * 
 * @author Tim
 * @see {@link OnInputChnagedListener}
 */
public class ArticleFragment extends Fragment implements TextWatcher {

	private static final String TAG = ArticleFragment.class.getSimpleName();

	OnInputChnagedListener onInputChnagedListener;
	EditText inputEditText;
	View inflate;

	public interface OnInputChnagedListener {
		public void onInputChanged(String newText);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		inflate = inflater.inflate(R.layout.article, container, false);
		inputEditText = (EditText) inflate.findViewById(R.id.input);
		inputEditText.addTextChangedListener(this);
		return inflate;
	}

	/**
	 * @throws ClassCastException
	 *             if activity doesn't implement {@link OnInputChnagedListener}
	 */
	@Override
	public void onAttach(Activity activity) {
		try {
			onInputChnagedListener = (OnInputChnagedListener) activity;
		} catch (ClassCastException exception) {
			String detailMessage = String.format("%s should implement %s",
					activity.toString(),
					OnInputChnagedListener.class.getSimpleName());
			throw new ClassCastException(detailMessage);
		}
		Log.d(TAG, "onAtach()");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate() and >" + Boolean.toString(getView() == inflate));
		// inputEditText = (EditText) getView().findViewById(R.id.input);
		// inputEditText.addTextChangedListener(this);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView()");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestory()");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d(TAG, "onDetach()");
		super.onDetach();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		onInputChnagedListener.onInputChanged(inputEditText.getText()
				.toString());
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
