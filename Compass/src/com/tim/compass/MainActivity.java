package com.tim.compass;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	Button inputButton;
	EditText inputEditText;
	CompassView cView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cView = (CompassView) this.findViewById(R.id.compassView);
		cView.setBearing(0);

		inputButton = (Button) findViewById(R.id.inputButtom);
		inputEditText = (EditText) findViewById(R.id.inputEditText);

		inputButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String inputString = inputEditText.getText().toString();

		try {
			float inputFloat = Float.parseFloat(inputString);
			cView.setBearing(inputFloat);
			Log.d(TAG, "inputFloat = " + inputString);
		} catch (NumberFormatException e) {
			Toast.makeText(this, "you need to input the number",
					Toast.LENGTH_LONG).show();
			;
		}
	}

}
