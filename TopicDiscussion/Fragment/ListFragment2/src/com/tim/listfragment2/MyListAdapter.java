package com.tim.listfragment2;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

	private static final String TAG = MyListAdapter.class.getSimpleName();

	private final ArrayList<File> mItems = new ArrayList();

	private Context mContext;

	private MyListAdapter() {

	}

	public MyListAdapter(Context context) {
		this();
		mContext = context;
		File dirPath = mContext.getFilesDir();
		for (String filename : mContext.fileList()) {
			mItems.add(new File(dirPath, filename));
		}

	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView = inflater.inflate(R.layout.row, parent, false);
		// MyItem mItem = mItems.get(position);
		File mItem = mItems.get(position);

		ImageView mImageView = (ImageView) mView.findViewById(R.id.imageView1);
		mImageView.setImageResource(android.R.drawable.stat_notify_chat);

		TextView mTextView = (TextView) mView.findViewById(R.id.textView1);
		mTextView.setText(mItem.getName());

		TextView mTimeTextView = (TextView) mView.findViewById(R.id.textView2);
		// mTimeTextView.setText(mItem.getTime().toLocaleString());
		mTimeTextView.setText(new Date(mItem.lastModified()).toString());

		Log.v(TAG, String.format("getView position is %d", position));
		return mView;
	}

	// public static class MyItem {
	// private int mIcon;
	// private String mText;
	// private Date mTime;
	//
	// /**
	// * @param mIcon
	// * @param mText
	// * @param mTime
	// */
	// public MyItem(int mIcon, String mText, Date mTime) {
	// super();
	// this.mIcon = mIcon;
	// this.mText = mText;
	// this.mTime = mTime;
	// }
	//
	// public MyItem() {
	// mIcon = android.R.drawable.ic_media_ff;
	// mText = "DEFAULT";
	// mTime = new Date((long) (Long.MAX_VALUE * Math.random()));
	// }
	//
	// public int getmIcon() {
	// return mIcon;
	// }
	//
	// public String getmText() {
	// return mText;
	// }
	//
	// public Date getTime() {
	// return mTime;
	// }
	//
	//
	// }

}
