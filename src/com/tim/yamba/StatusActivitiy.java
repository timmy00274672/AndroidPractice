package com.tim.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.R.integer;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StatusActivitiy extends Activity {
	private static int LIMIT=140;
	EditText editText;
	TextView textCount;
	Button updateButton;
	Twitter twitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);

		editText = (EditText) findViewById(R.id.editText);
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		textCount = (TextView) findViewById(R.id.textCount);

		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");

		updateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String temp = editText.getText().toString();
				StatusActivitiy.this.new PostToTwitter().execute(temp);

			}
		});

		textCount.setText(Integer.toString(StatusActivitiy.LIMIT));
		textCount.setTextColor(Color.GREEN);

		editText.addTextChangedListener(new TextWatcher() {
			CharSequence oldSequence= "";

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
				int count = StatusActivitiy.LIMIT - statusText.length();
				textCount.setText(Integer.toString(count));

				if (count < 0) {
//					CharSequence tempSequence = statusText.subSequence(0, 140);
					editText.setText(oldSequence);
					editText.setSelection(StatusActivitiy.LIMIT);
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
		getMenuInflater().inflate(R.menu.status_activitiy, menu);
		return true;
	}

	class PostToTwitter extends AsyncTask<String, Integer, String> {

		//
		@Override
		protected String doInBackground(String... statuses) {
			try {
				Twitter.Status status = twitter.updateStatus(statuses[0]);
				return status.text;
			} catch (TwitterException e) {
				Log.e(StatusActivitiy.class.getName(), e.toString());
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
			Toast.makeText(StatusActivitiy.this, result, Toast.LENGTH_LONG)
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
}
