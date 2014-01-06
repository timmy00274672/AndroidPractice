package com.tim.fragmentcontextmenu;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;

public class FragmentContextMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ContextMenuFragment content = new ContextMenuFragment();
		getFragmentManager().beginTransaction()
				.add(android.R.id.content, content).commit();
	}

	public static class ContextMenuFragment extends Fragment {
		private static final int A_ITEM_ID = 2014010601;
		private static final int B_ITEM_ID = 2014010602;
		private static final String TAG = ContextMenuFragment.class.getSimpleName();

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_context_menu,
					container, false);
			registerForContextMenu(root.findViewById(R.id.long_press));
			return root;
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.add(Menu.NONE, A_ITEM_ID, Menu.NONE, "Menu A");
			menu.add(Menu.NONE, B_ITEM_ID, Menu.NONE, "Menu B");
		}
		
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			Log.v(TAG,String.format("onContextItemSelected item:%s", item.getTitle()));
			return true;
		}
	}

}
