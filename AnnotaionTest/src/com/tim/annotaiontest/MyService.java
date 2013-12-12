package com.tim.annotaiontest;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.UiThread;

@EService
public class MyService extends IntentService {

	public MyService() {
		super(MyService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// Do some stuff...

		showToast();
	}

	@UiThread
	void showToast() {
		Toast.makeText(getApplicationContext(), "Hello World!",
				Toast.LENGTH_LONG).show();
	}

}
