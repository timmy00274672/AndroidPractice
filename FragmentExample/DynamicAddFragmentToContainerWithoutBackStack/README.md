Read that [MainActivity](src/com/tim/fragment/example/MainActivity.java).

I use a button to toggle the existing of [ArticleFragment](src/com/tim/fragment/example/ArticleFragment.java).

In this version:

```java

	@Override
	public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();
		ArticleFragment articleFragment = (ArticleFragment) fragmentManager
				.findFragmentById(R.id.framelayout1);
		if (articleFragment == null) {
			Log.d(TAG,"there is no articleFragment");
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			articleFragment = new ArticleFragment();
			fragmentTransaction.add(R.id.framelayout1, articleFragment, "test");
			fragmentTransaction.commit();
		}else {
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.remove(articleFragment);
			fragmentTransaction.commit();
		}
	}
```

If button is pressed when no ArticleFragment exists, here will new a ArticleFragment and dynamically add to the layout.

If button is pressed when an ArticleFragment exist, here will remove from the layout, and the ArticleFragment will be paused, stoped, destoryViewed, destoryed and finally detach, so articleFragment==null as the next button pressed.
