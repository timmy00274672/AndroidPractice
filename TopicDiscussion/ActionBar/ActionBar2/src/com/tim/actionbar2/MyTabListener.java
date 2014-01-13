package com.tim.actionbar2;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MyTabListener<T extends Fragment> implements TabListener {

	private static final String TAG = MyTabListener.class.getSimpleName();
	private Activity mActivity;
	private String mTag;
	private Class<T> mClass;
	private Bundle mArgs;
	private FragmentManager mFragmentManager;
	private Fragment mFragment;

	public MyTabListener(Activity activity, String tag, Class<T> clz,
			Bundle args) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mArgs = args;

		mFragmentManager = mActivity.getFragmentManager();
		mFragment = mFragmentManager.findFragmentByTag(mTag);
		if (mFragment != null && !mFragment.isDetached()) {
			FragmentTransaction transaction = mFragmentManager
					.beginTransaction();
			transaction.detach(mFragment);
			transaction.commit();
		}

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.v(TAG, "onTabSelected");
		if (mFragment == null) {
			Log.v(TAG, "mFrag is null");
			mFragment = Fragment
					.instantiate(mActivity, mClass.getName(), mArgs);
			ft.add(android.R.id.content, mFragment, mTag);
			Log.v(TAG, "tabselect and added");
		} else {
			ft.attach(mFragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null) {
			ft.detach(mFragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

}
