package com.tim.actionbar1;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View returnView = inflater.inflate(R.layout.image1, container, false);
		return returnView;
	}

	public class Listener implements ActionBar.TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(android.R.id.content, AFragment.this, null);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(AFragment.this);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

	}
}