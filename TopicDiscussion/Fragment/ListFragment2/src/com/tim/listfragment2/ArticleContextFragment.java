package com.tim.listfragment2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ArticleContextFragment extends Fragment {

	public int getPosition() {
		return getArguments().getInt(ArticleListFragment.K_POSITION, 0);
	}

	public static ArticleContextFragment newInstance(int mPosition) {
		Bundle args = new Bundle();
		args.putInt(ArticleListFragment.K_POSITION, mPosition);
		ArticleContextFragment fragment = new ArticleContextFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		EditText mEditText= new EditText(getActivity());
		mEditText.setHint(Integer.toString(getPosition()));
		return mEditText;
	}
}
