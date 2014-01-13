package com.tim.listfragment2;

import com.tim.listfragment2.MyListAdapter.MyItem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleListFragment extends ListFragment {

	public static final String K_POSITION = "K_POSITION";
	private static final String TAG = ArticleListFragment.class.getSimpleName();
	public static final int NONSELECT_POSITION = Integer.MIN_VALUE;
	private boolean mDualPane;
	private int mPosition = NONSELECT_POSITION;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		super.setHasOptionsMenu(true);
		mDualPane = isDualPane();

		if (savedInstanceState != null) {
			mPosition = savedInstanceState.getInt(K_POSITION, 0);
		}

		// if (mDualPane) {
		// // show the context
		// getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		// if (!getListAdapter().isEmpty()) {
		// showContext(mPosition);
		// } else {
		//
		// }
		// Log.v(TAG, String.format("the initial position is %d", mPosition));
		// }

	}

	private void showContext(int target) {
		mPosition = target;

		getListView().setItemChecked(mPosition, true);

		if (mDualPane) {

			// change context when fragment doesn't exist the old one is not
			// equal to the target one

			FragmentManager fm = getActivity().getFragmentManager();
			ArticleContextFragment context = (ArticleContextFragment) fm
					.findFragmentById(R.id.contextFragment);

			if (context == null || context.getPosition() != mPosition) {
				FragmentTransaction ft = fm.beginTransaction();
				context = ArticleContextFragment.newInstance(mPosition);
				ft.replace(R.id.contextFragment, context);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			Toast.makeText(getActivity(), String.format("UndoNow", null),
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isDualPane() {

		return getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "test");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle() == "test") {
			Toast.makeText(getActivity(),
					String.format("%s is select, and to be processed", "test"),
					Toast.LENGTH_SHORT).show();
			Toast.makeText(
					getActivity(),
					String.format("ListView does %s exist",
							getListView() == null ? "not" : ""),
					Toast.LENGTH_SHORT).show();
			test();
			return true;
		}
		return false;
	}

	private void test2() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView = inflater.inflate(R.layout.row, getListView(), false);
		MyItem mItem = new MyItem();

		ImageView mImageView = (ImageView) mView.findViewById(R.id.imageView1);
		mImageView.setImageResource(mItem.getmIcon());

		TextView mTextView = (TextView) mView.findViewById(R.id.textView1);
		mTextView.setText(mItem.getmText());

		TextView mTimeTextView = (TextView) mView.findViewById(R.id.textView2);
		mTimeTextView.setText(mItem.getTime().toLocaleString());
//		Log.v(TAG, String.format("getView position is %d", position));
		getActivity().setContentView(mView);
	}

	private void test() {
		MyListAdapter adapter = new MyListAdapter(getActivity());
		setListAdapter(adapter);
		setListShown(true);
		adapter.notifyDataSetChanged();
		Log.v(TAG, String.format("Count = %d", adapter.getCount()));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showContext(position);
	}
}
