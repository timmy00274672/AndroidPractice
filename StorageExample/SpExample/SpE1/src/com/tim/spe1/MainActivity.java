package com.tim.spe1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected static final String FILENAME = MainActivity.class.getName();
	protected static final String INPUTKEY = "KEY1";
	Button button;
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.button1);
		editText = (EditText) findViewById(R.id.editText1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sharedPreferences = getSharedPreferences(FILENAME, MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				String input = MainActivity.this.editText.getText().toString();
				editor.putString(INPUTKEY, input);
				String text = null;
				if(editor.commit()){
					text = String.format("%s = %s ", INPUTKEY,input);
				}else {
					text = "nothing stored";
				}
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
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
