package com.tim.yamba;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TimelineAdapter extends SimpleCursorAdapter {
	private static String[] FROM = { StatusData.C_CREATED_AT,
			StatusData.C_USER, StatusData.C_TEXT };
	private static int[] TO = { R.id.textCreatedAt, R.id.textUser,
			R.id.textText };

	@SuppressWarnings("deprecation")
	public TimelineAdapter(Context context, Cursor cursor) {
		super(context, R.layout.row, cursor, FROM, TO);
	}

	/**
	 * @see android.widget.SimpleCursorAdapter#bindView(android.view.View,
	 *      android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		super.bindView(view, context, cursor);

		long time = cursor.getLong(cursor
				.getColumnIndex(StatusData.C_CREATED_AT));
		TextView textView = (TextView)view.findViewById(R.id.textCreatedAt);
		textView.setText(DateUtils.getRelativeTimeSpanString(time));
	}

}
