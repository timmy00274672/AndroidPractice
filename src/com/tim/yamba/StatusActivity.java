package com.tim.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>NOTE</b>:SharedPreferences keeps listeners in a WeakHashMap. This means
 * that you cannot use an anonymous inner class as a listener, as it will become
 * the target of garbage collection as soon as you leave the current scope. <a
 * href=
 * "http://stackoverflow.com/questions/2542938/sharedpreferences-onsharedpreferencechangelistener-not-being-called-consistently/3104265#3104265"
 * > reference</a>
 * 
 * @author Tim
 * 
 */
public class StatusActivity extends Activity {
	/**
	 * The limit of characters to the status.
	 */
	private static int LIMIT = 140;
	EditText editText;
	TextView textCount;
	Button updateButton;

	/**
	 * details:
	 * <ul>
	 * <li>confirms that characters in {@link #editText} less than or equal to
	 * {@link #LIMIT}</li>
	 * <li>if values in {@link #prefs} changed, {@link #twitter} need to be
	 * updated.</li>
	 * <li>implements the function of {@link #updateButton}</li>
	 * </ul>
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);

		editText = (EditText) findViewById(R.id.editText);
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		textCount = (TextView) findViewById(R.id.textCount);

		updateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String temp = editText.getText().toString();
				StatusActivity.this.new PostToTwitter().execute(temp);

			}
		});

		textCount.setText(Integer.toString(StatusActivity.LIMIT));
		textCount.setTextColor(Color.GREEN);

		editText.addTextChangedListener(new TextWatcher() {
			CharSequence oldSequence = "";

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable statusText) {
				Log.d("in afterTextChanged input", statusText.toString());
				Log.d("in afterTextChanged old", oldSequence.toString());
				int count = StatusActivity.LIMIT - statusText.length();
				textCount.setText(Integer.toString(count));

				if (count < 0) {
					// CharSequence tempSequence = statusText.subSequence(0,
					// 140);
					editText.setText(oldSequence);
					editText.setSelection(StatusActivity.LIMIT);
					textCount.setTextColor(Color.RED);
					return;
				}
				if (count == 0) {
					textCount.setTextColor(Color.RED);
				} else if (count <= 10) {
					textCount.setTextColor(Color.YELLOW);
				} else {
					textCount.setTextColor(Color.GREEN);
				}
				oldSequence = statusText.toString();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Two method to deal with connecting
	 * <ul>
	 * <li>use the same thread (main thread) to connect</li>
	 * <li>use asynchronous method</li>
	 * </ul>
	 * Newer version <b>prevents </b> the first one, because it may seriously
	 * delay the main thread. Such that android provides
	 * {@link android.os.AsyncTask<String, Integer, String>} </br> note:
	 * <ul>
	 * <li>each generic type is for each method in this abstract class</li>
	 * <li>The return object of {@link #doInBackground(String...)} is passed to
	 * {@link #onPostExecute(String)}</li>
	 * <li>If we need to update something depending on what we have done, we use
	 * the method {@link #onProgressUpdate(Integer...)} in
	 * {@link #doInBackground(String...)} For example, we need to show status
	 * bar, or installation details</li>
	 * </ul>
	 * 
	 * @author Tim
	 * 
	 */
	class PostToTwitter extends AsyncTask<String, Integer, String> {

		//
		@Override
		protected String doInBackground(String... statuses) {
			try {
				Twitter.Status status = ((YambaApplication) getApplication())
						.getTwitter().updateStatus(statuses[0]);
				return status.text;
			} catch (TwitterException e) {
				Log.e(StatusActivity.class.getName(), e.toString());
				e.printStackTrace();
				return "failed to post";
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

		}

	}

	/**
	 * Key points:
	 * <ul>
	 * <li>Use intent to call other activity, with {@link #StatusActivity()}</li>
	 * <li>here we use {@link #startActivity}, {@link #startService} ,
	 * {@link #stopService} to control service and connecting other activities
	 * </ul>
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		case R.id.itemServiceStart:
			startService(new Intent(this, UpdaterService.class));
			break;
		case R.id.itemServiceStop:
			stopService(new Intent(this, UpdaterService.class));
			break;
		case R.id.debug:
			startService(new Intent(this,DebugMain.class));
			stopService(new Intent(this,DebugMain.class));
		}
		return true;
	}

}
