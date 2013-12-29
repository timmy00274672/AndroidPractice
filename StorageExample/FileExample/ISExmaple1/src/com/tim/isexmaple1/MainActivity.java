package com.tim.isexmaple1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getSimpleName();
	EditText filenameEditText, dataEditText;
	Button saveButton, cleanButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		filenameEditText = (EditText) findViewById(R.id.editTextFileName);
		dataEditText = (EditText) findViewById(R.id.editTextFileData);

		saveButton = (Button) findViewById(R.id.buttonSave);
		cleanButton = (Button) findViewById(R.id.buttonClean);

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					FileOutputStream outputStream = openFileOutput(
							getFileName(), MODE_PRIVATE);
					outputStream.write(getData().getBytes());
					outputStream.close();
					Toast.makeText(MainActivity.this, "saved",
							Toast.LENGTH_LONG).show();
					Log.v(TAG, "saved");
				} catch (FileNotFoundException e) {
					Log.e(TAG, "FileNotFoundException");
				} catch (IOException e) {
					Log.e(TAG, "IOException");
				}
			}
		});
		cleanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dataEditText.setText("");
				filenameEditText.setText("");
			}
		});

	}

	private String getFileName() {
		return filenameEditText.getText().toString();
	}

	private String getData() {
		return dataEditText.getText().toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_loads:
			selectFile(1);// 1 for load files
			return true;
		case R.id.action_delete:
			selectFile(2); // 2 for delete file
			return true;
		default:
			return false;
		}

	}

	/**
	 * 
	 * @param requestType
	 *            1:load files; 2:delete file
	 * @return
	 */
	private String selectFile(int requestType) {
		Intent intent = new Intent();
		intent.setClass(this, FileListActivity.class);
		startActivityForResult(intent, requestType);// 1 for load
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK || data == null
				|| data.getStringExtra("FILENAME") == null)
			return;
		String filename = data.getStringExtra("FILENAME");
		Log.v(TAG, filename + " is return ");
		switch (requestCode) {
		case 1:
			loadFile(filename);
			break;
		case 2:
			deleteFile(filename);
			cleanButton.callOnClick();
		default:
			break;
		}

	}

	private void loadFile(String filename) {
		filenameEditText.setText(filename);
		try {
			FileInputStream inputStream = openFileInput(filename);
			byte[] buffer = new byte[inputStream.available()];
			int readNum;
			StringBuffer sBuffer = new StringBuffer();
			if ((readNum = inputStream.read(buffer)) != -1) {
				sBuffer.append(new String(buffer, 0, readNum));
			}
			dataEditText.setText(sBuffer);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}
