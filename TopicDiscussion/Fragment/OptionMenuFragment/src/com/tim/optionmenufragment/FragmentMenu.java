package com.tim.optionmenufragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class FragmentMenu extends Activity {

	private static final String TAG = FragmentMenu.class.getSimpleName();
	private Fragment mFragment1;
	private Fragment mFragment2;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			updateFragmentVisibility();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu);

		// Make sure the two menu fragments are created.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mFragment1 = fm.findFragmentByTag("f1");
		if (mFragment1 == null) {
			mFragment1 = new MenuFragment();
			ft.add(mFragment1, "f1");
		}
		mFragment2 = fm.findFragmentByTag("f2");
		if (mFragment2 == null) {
			mFragment2 = new Menu2Fragment();
			ft.add(mFragment2, "f2");
		}
		ft.commit();

		// Watch check box clicks.
		mCheckBox1 = (CheckBox) findViewById(R.id.menu1);
		mCheckBox1.setOnClickListener(mClickListener);
		mCheckBox2 = (CheckBox) findViewById(R.id.menu2);
		mCheckBox2.setOnClickListener(mClickListener);

		// Make sure fragments start out with correct visibility.
		updateFragmentVisibility();
	}

	private void updateFragmentVisibility() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (mCheckBox1.isChecked())
			ft.show(mFragment1);
		else
			ft.hide(mFragment1);
		if (mCheckBox2.isChecked())
			ft.show(mFragment2);
		else
			ft.hide(mFragment2);
		ft.commit();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		updateFragmentVisibility();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.v(TAG, String.format("in %s, onOptionItemSelected: item=%s",
				TAG, item.getTitle()));
		return false;
	}

	/**
	 * A fragment that displays a menu. This fragment happens to not have a UI
	 * (it does not implement onCreateView), but it could also have one if it
	 * wanted.
	 */
	public static class MenuFragment extends Fragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			menu.add("Menu 1a")
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add("Menu 1b")
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}

	/**
	 * Second fragment with a menu.
	 */
	public static class Menu2Fragment extends Fragment {
		String TAG = Menu2Fragment.class.getSimpleName();
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			menu.add("Menu 2").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			Log.v(TAG, String.format("in %s, onOptionItemSelected: item=%s",
					TAG, item.getTitle()));
			return false;
		}
	}
}
