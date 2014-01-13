package com.tim.listfragment2;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean mDualPane = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		Toast.makeText(
				this,
				String.format("orientation is %s", mDualPane ? "Dual"
						: "Portrait"), Toast.LENGTH_SHORT).show();
		setContentView(R.layout.main);
	}


}
