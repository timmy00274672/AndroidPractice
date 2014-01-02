package com.tim.isexmaple1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getSimpleName();
	public static final int ACTION_SELECTFILE = 1;
	private static final String[] FILE_OPERATIONS = { "LOAD", "RENAME",
			"DELETE" };
	EditText filenameEditText, dataEditText;
	Button saveButton, cleanButton;
	private String selectFileNmae = null;
	protected String newFileName;

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
				saveInternal();
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
		case R.id.itemFileSelect:
			selectFile();
			return true;
		case R.id.itemTest:
			testPopwindow();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void testPopwindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View popView = inflater
				.inflate(R.layout.file_operation_popwindow, null);
		final EditText testEditText = (EditText) popView
				.findViewById(R.id.editTextNewName);
		Button testButton = (Button) popView.findViewById(R.id.buttonComfirm);
		Point outSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(outSize);
		final PopupWindow popWindow = new PopupWindow(popView, (outSize.x) / 2,
				(outSize.y) / 2, true);
		testButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newFileName = testEditText.getText().toString();
				changeName();
				Toast.makeText(MainActivity.this,
						testEditText.getText().toString(), Toast.LENGTH_LONG)
						.show();
				popWindow.dismiss();
			}
		});
		popWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
	}

	protected void changeName() {
		changeName(selectFileNmae, newFileName);
	}

	/**
	 * 
	 * @param requestType
	 *            1:select file
	 * @return
	 */
	private void selectFile() {
		Intent intent = new Intent();
		intent.setClass(this, FileListActivity.class);
		startActivityForResult(intent, ACTION_SELECTFILE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK || data == null
				|| data.getStringExtra("FILENAME") == null)
			return;
		final String filename = data.getStringExtra("FILENAME");
		selectFileNmae = filename;
		Log.v(TAG, selectFileNmae + " is return ");
		switch (requestCode) {
		case ACTION_SELECTFILE:
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle(R.string.fileOperationTitle);
			android.content.DialogInterface.OnClickListener listener = new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:// load
						loadFile(filename);
						dialog.cancel();
						break;
					case 1: // rename
						dialog.cancel();
						renameDialog();
						break;
					case 2:// delete
						deleteOk(filename);
						dialog.cancel();
						break;
					default:
						break;
					}

				}
			};
			builder.setSingleChoiceItems(FILE_OPERATIONS, 0, listener).create()
					.show();
			;
			// loadFile(filename);
			// break;
			// case 2:
			// deleteOk(filename);
		default:
			break;
		}

	}

	/**
	 * show a dialog to input the new name
	 * 
	 * @param filename
	 *            the file should be change
	 */
	protected void renameDialog() {
		testPopwindow();
	}

	private void changeName(String oldName, String newName) {
		try {
			FileInputStream inputStream = openFileInput(oldName);
			FileOutputStream outputStream = openFileOutput(newName,
					MODE_PRIVATE);

			byte[] buffer = new byte[1024 * 4];
			int readNum;
			while ((readNum = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, readNum);
			}
			deleteFile(oldName);
			loadFile(newName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ask user if delete
	private void deleteOk(final String filename) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Warning");
		builder.setMessage(String.format("do you really want to remove %s",
				filename));
		builder.setPositiveButton(R.string.confirmYes,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteFile(filename);
					}

				});
		builder.setNegativeButton(R.string.confirmNo,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this,
								String.format("%s is not removed", filename),
								Toast.LENGTH_LONG).show();
					}
				});
		builder.show();
	}

	public boolean deleteFile(String name) {
		super.deleteFile(name);
		cleanButton.callOnClick();
		Toast.makeText(MainActivity.this, String.format("%s is removed", name),
				Toast.LENGTH_LONG).show();
		return true;

	};

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
			selectFileNmae = filename;
			newFileName = null;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private void saveInternal() {
		try {
			FileOutputStream outputStream = openFileOutput(getFileName(),
					MODE_PRIVATE);
			outputStream.write(getData().getBytes());
			outputStream.close();
			Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_LONG)
					.show();
			Log.v(TAG, "saved");
		} catch (FileNotFoundException e) {
			Log.e(TAG, "FileNotFoundException");
		} catch (IOException e) {
			Log.e(TAG, "IOException");
		}
	}
}
