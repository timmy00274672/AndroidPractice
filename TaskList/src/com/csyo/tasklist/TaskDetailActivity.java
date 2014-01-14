package com.csyo.tasklist;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csyo.tasklist.TaskList.Tasks;

public class TaskDetailActivity extends ListActivity {

	public static final String TAG = TaskDetailActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private ListView listView;
	private TextView dateName, timeName;
	protected CheckedTextView ctvOnOff, ctvAlarm;
	private TextView contentName, contentDesc;
	private static TextView timeDesc, dateDesc;
	private static int mYear;
	private static int mMonth;
	private static int mDay;
	private static int mHour;
	private static int mMinute;
	private String content, date, time;
	protected int on_off;
	private int id;
	private int alarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		inflater = getLayoutInflater();

		listView = getListView();
		listView.setAdapter(new ViewAdapter());
		listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					ctvOnOff = (CheckedTextView) view;
					on_off = ctvOnOff.isChecked() ? 0 : 1;
					break;
				case 1:
					showDatePickerDialog();
					break;
				case 2:
					showTimePickerDialog();
					break;
				case 3:
					showContentDialog("Enter content: ");
					break;
				case 4:
					ctvAlarm = (CheckedTextView) view;
					if (ctvAlarm.isChecked()) {
						alarm = 0;
						setAlarm(false);
					} else {
						alarm = 1;
						setAlarm(true);
					}
					break;
				}
			}
		});
	}

	protected void setAlarm(boolean flag) {
		final String BC_ACTION = "com.csyo.tasklist.TaskReceiver";
		final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent();
		intent.setAction(BC_ACTION);
		intent.putExtra("msg", content);
		final PendingIntent pi = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent, 0);
		final long systemTime = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay, mHour, mMinute);
		long taskTime = c.getTimeInMillis();
		if (flag && (taskTime - systemTime) > 0 && on_off == 1)
			am.set(AlarmManager.RTC_WAKEUP, taskTime, pi);
		else
			am.cancel(pi);
	}

	protected void showContentDialog(String msg) {
		View view = inflater.inflate(R.layout.item_content, null);
		final EditText editText = (EditText) view.findViewById(R.id.content);
		editText.setText(content);
		new AlertDialog.Builder(this).setView(view).setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("Done", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						content = editText.getText().toString();
						contentDesc.setText(content);
					}
				}).show();
	}

	protected void showDatePickerDialog() {
		DialogFragment fragment = new DatePickerFragment();
		fragment.show(getFragmentManager(), "datePicker");
	}

	protected void showTimePickerDialog() {
		DialogFragment fragment = new TimePickerFragment();
		fragment.show(getFragmentManager(), "timePicker");
	}

	@Override
	protected void onResume() {
		super.onResume();
		init(getIntent());
	}

	private void init(Intent intent) {
		Bundle b = intent.getBundleExtra("bundle");
		if (b != null) {
			id = b.getInt("id");
			content = b.getString("content");
			date = b.getString("date");
			time = b.getString("time");
			on_off = b.getInt("on_off");
			alarm = b.getInt("alarm");

			if (date != null && date.length() > 0) {
				String[] strs = date.split("/");
				mYear = Integer.parseInt(strs[0]);
				mMonth = Integer.parseInt(strs[1]);
				mDay = Integer.parseInt(strs[2]);
			}

			if (time != null && time.length() > 0) {
				String[] strs = time.split(":");
				mHour = Integer.parseInt(strs[0]);
				mMinute = Integer.parseInt(strs[1]);
			}
		}
	}

	class ViewAdapter extends BaseAdapter {

		String[] strs = { "ON / OFF", "Date", "Time", "Content", "Open Alarm" };

		@Override
		public int getCount() {
			return strs.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.item_row, null);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView desc = (TextView) view.findViewById(R.id.desc);
			switch (position) {
			case 0:
				ctvOnOff = (CheckedTextView) inflater
						.inflate(
								android.R.layout.simple_list_item_multiple_choice,
								null);
				ctvOnOff.setText(strs[position]);
				ctvOnOff.setTextSize(30);
				ctvOnOff.setChecked(on_off == 0 ? false : true);
				return ctvOnOff;
			case 1:
				dateName = name;
				dateDesc = desc;
				dateName.setText(strs[position]);
				dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
				return view;
			case 2:
				timeName = name;
				timeDesc = desc;
				timeName.setText(strs[position]);
				timeDesc.setText(mHour + ":" + mMinute);
				return view;
			case 3:
				contentName = name;
				contentDesc = desc;
				contentName.setText(strs[position]);
				contentDesc.setText(content);
				return view;
			case 4:
				ctvAlarm = (CheckedTextView) inflater
						.inflate(
								android.R.layout.simple_list_item_multiple_choice,
								null);
				ctvAlarm.setText(strs[position]);
				ctvAlarm.setTextSize(30);
				ctvAlarm.setChecked(alarm == 0 ? false : true);
				return ctvAlarm;
			}
			return null;
		}

	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mYear = year;
			mMonth = month + 1;
			mDay = day;
			String Date = mYear + "/" + mMonth + "/" + mDay;
			Log.d(TAG, Date);
			dateDesc.setText(Date);
		}
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new TimePickerDialog(getActivity(), this, mHour, mMinute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			timeDesc.setText(mHour + ":" + mMinute);
		}
	}

	@Override
	protected void onPause() {
		saveOrUpdate();
		super.onPause();
	}

	private void saveOrUpdate() {
		ContentValues values = new ContentValues();
		values.clear();
		values.put(Tasks.CONTENT, contentDesc.getText().toString());
		values.put(Tasks.DATE, dateDesc.getText().toString());
		values.put(Tasks.TIME, timeDesc.getText().toString());
		values.put(Tasks.ON_OFF, ctvOnOff.isChecked() ? 1 : 0);
		values.put(Tasks.ALARM, ctvAlarm.isChecked() ? 1 : 0);
		if (id != 0){
			// update
			Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
			getContentResolver().update(uri, values, null, null);
		} else {
			Uri uri = Tasks.CONTENT_URI;
			getContentResolver().insert(uri, values);
		}
	}
}
