package com.csyo.tasklist;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity {

	private static final int ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		Button btn = (Button) findViewById(R.id.btn_cancel);
		TextView txt = (TextView) findViewById(R.id.msg_TextView);
		String service = Context.NOTIFICATION_SERVICE;
		final NotificationManager sNManager = (NotificationManager) getSystemService(service);
		Notification notification = new Notification();
		
		String msg = getIntent().getStringExtra("msg");
		notification.tickerText = msg;
		txt.setText(msg);
		notification.sound = Uri.parse("file:///"+Environment.getExternalStorageDirectory().getPath()+"/fallbackring.ogg");
		sNManager.notify(ID, notification);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sNManager.cancel(ID);
				finish();
			}
		});
	}
}
