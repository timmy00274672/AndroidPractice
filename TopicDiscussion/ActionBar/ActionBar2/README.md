This is note for `ActionBar2`, which implements the same thing as `ActionBar1` in more general method.


## Generics Listener
We notice that the behavior of `ActionBar.TabListener` for each Tab is almost the same, so I extract the code via **generics** `ActionBar.TabListener`.

	```java
	
	public class MyTabListener<T extends Fragment> implements TabListener 
	```

In this generics `ActionBar.TabListener`, we use the method:

- Fragment.instantiate(Context context, String fname, Bundle args)
 	
	```java

	mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
	```

Notice the parameters of the constrctor:

	```java

	MyTabListener(Activity activity, String tag, Class<T> clz, Bundle args)
	```

## Deeper look

Always make sure that the state of fragment is right. Like:

1. In the constructor, detach the fragment if it's attached.
	
	```java
	
		mFragmentManager = mActivity.getFragmentManager();
		mFragment = mFragmentManager.findFragmentByTag(mTag);
		if (mFragment != null && !mFragment.isDetached()) {
			FragmentTransaction transaction = mFragmentManager
					.beginTransaction();
			transaction.detach(mFragment);
			transaction.commit();
	```

2. In the `onTabSelected` method, the fragment is only instantiated if it does not exist. If it exist, only attach it to the activity.
	
	```java
	
		if (mFragment == null) {
			Log.v(TAG, "mFrag is null");
			mFragment = Fragment
					.instantiate(mActivity, mClass.getName(), mArgs);
			ft.add(android.R.id.content, mFragment, mTag);
			Log.v(TAG, "tabselect and added");
		} else {
			ft.attach(mFragment);
		}
	```

3. In the `onTabUnselected` method, always detach the fragment.
	
	```java
	
		if (mFragment != null) {
			ft.detach(mFragment);
		}
	```
