package com.tim.dialogfragmentexample;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements
		MyCustomDialog.onSubmitListener {

	private TextView mTextView;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.textView1);
		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showMyDialog(0);
			}

		});
	}

	private void showMyDialog(int style) {
		// fragment1.show(getFragmentManager(), "");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		MyCustomDialog prev = (MyCustomDialog) getFragmentManager()
				.findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		prev = MyCustomDialog.getInstance(MainActivity.this, style);
		prev.show(ft, "dialog");

	}

	@Override
	public void setOnSubmitListener(int i) {
		mTextView.setText(Integer.toString(i));
		showMyDialog(i);
	}

}