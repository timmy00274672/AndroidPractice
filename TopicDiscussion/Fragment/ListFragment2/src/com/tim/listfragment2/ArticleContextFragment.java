package com.tim.listfragment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.transform.Templates;

import android.R.integer;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ArticleContextFragment extends Fragment {

	private static final String TAG = ArticleContextFragment.class
			.getSimpleName();
	// public int getPosition() {
	// return getArguments().getInt(ArticleListFragment.K_POSITION, 0);
	// }
	//
	// public static ArticleContextFragment newInstance(int mPosition) {
	// Bundle args = new Bundle();
	// args.putInt(ArticleListFragment.K_POSITION, mPosition);
	// ArticleContextFragment fragment = new ArticleContextFragment();
	// fragment.setArguments(args);
	// return fragment;
	// }
	private File mFile;

	public static ArticleContextFragment newInstance(File item) {
		ArticleContextFragment lFragment = new ArticleContextFragment();
		lFragment.mFile = item;
		return lFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		EditText mEditText = new EditText(getActivity());
		mEditText.setText(readText());
		return mEditText;
	}

	private CharSequence readText() {
		StringBuffer lBuffer = new StringBuffer();
		String tempString = null;
		try {
			// FileReader = new FileReader(mFile);
			FileInputStream lStream = new FileInputStream(mFile);
			// BufferedReader lReader = new BufferedReader(new
			// FileReader(mFile));
			// while ((tempString = lReader.readLine()) != null) {
			// lBuffer.append(tempString);
			// }
			// lReader.close();

			byte[] buffer = new byte[lStream.available()];
			int count;
			while ((count = lStream.read(buffer)) != -1) {
				lBuffer.append(new String(buffer, 0, count));
			}
			tempString = lBuffer.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v(TAG, String.format("File(%s) : %s", mFile.toString(), tempString));
		return tempString;
	}

	public File getFile() {
		return mFile;
	}
}
