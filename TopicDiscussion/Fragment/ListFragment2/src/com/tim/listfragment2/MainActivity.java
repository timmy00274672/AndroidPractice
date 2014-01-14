package com.tim.listfragment2;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("ADD FILE FOR TEST");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("ADD FILE FOR TEST")) {
			String filename = Long
					.toHexString((long) (Math.random() * Long.MAX_VALUE));
			try {
				BufferedOutputStream mStream = new BufferedOutputStream(openFileOutput(filename, Context.MODE_PRIVATE));

				mStream.write(filename.getBytes());
				
				mStream.close();
			} catch (IOException e) {
				Toast.makeText(
						this,
						String.format("File(%s) can not be opened or created",
								filename), Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return false;
	}
}
