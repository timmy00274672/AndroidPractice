package com.tim.moneybook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends BaseActivity {
	protected static final String TAG = AddItemActivity.class.getName();
	Button addButton;
	Button cancelButton;
	EditText timeText;
	EditText amountText;
	EditText psText;
	

	private void clearEditTexts() {
		timeText.setText("");
		amountText.setText("");
		psText.setText("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_view);
		addButton = (Button) findViewById(R.id.buttomAdd);
		cancelButton = (Button) findViewById(R.id.buttomCanel);
		amountText = (EditText) findViewById(R.id.editTextAmount);
		psText = (EditText) findViewById(R.id.editTextPs);
		timeText = (EditText) findViewById(R.id.editTextTime);
		

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = String.format("time is %s \t amount=$%s", timeText
						.getText().toString(), amountText.getText().toString());
				Log.d(TAG, msg);
				application.getDbHelper().insertData(
						timeText.getText().toString(),
						amountText.getText().toString(),
						psText.getText().toString());
				clearEditTexts();
				Toast.makeText(AddItemActivity.this, msg, Toast.LENGTH_LONG).show();;
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearEditTexts();
				
			}
		});
	}
	
	
}
