package com.tim.demopref;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected static final String TAG = MainActivity.class.getSimpleName();
	EditText bInput;
	Button mButton, mButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SharedPreferences settings = getSharedPreferences("Test",
				Context.MODE_PRIVATE);
		bInput = (EditText) findViewById(R.id.editText1);
		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int inputInt = Integer.parseInt(bInput.getText().toString());
				Log.d(TAG, Integer.toString(inputInt));
				Editor editor = settings.edit();
				editor.putInt("TEMP", inputInt);
				editor.commit();
			}
		});
		mButton2 = (Button) findViewById(R.id.button2);
		mButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final SharedPreferences settings = getSharedPreferences("Test",
						Context.MODE_PRIVATE);
				int int1 = settings.getInt("TEMP", Integer.MAX_VALUE);
				Toast.makeText(MainActivity.this, Integer.toString(int1),
						Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
