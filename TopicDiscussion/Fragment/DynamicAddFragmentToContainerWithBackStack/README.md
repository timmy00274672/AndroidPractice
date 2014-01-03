This is readme file of `DynamicAddFragmentToContainerWithBackStack` project.

Sometime we dynamic add or any transaction, and we hope that the user press the *back key* to revert this transaction. We can use back stack via [FragmentTransaction#addToBackStack](http://developer.android.com/reference/android/app/FragmentTransaction.html#addToBackStack%28java.lang.String%29) method. Example:

```java
	articleFragment = new ArticleFragment();
	FragmentTransaction fragmentTransaction = fragmentManager
			.beginTransaction();
	fragmentTransaction.add(R.id.framelayout1, articleFragment, "test");
	fragmentTransaction.addToBackStack(null);
	fragmentTransaction.commit();
```

Try press the button and revert by press back key.

By the way, I use a default animation.

```java
	fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
```