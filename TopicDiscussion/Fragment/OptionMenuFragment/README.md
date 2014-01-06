This is note for `OptionMenuFragment`.

## The naming convention

Non-public, non-static field names start with **m**.
Static field names start with **s**.
Other fields start with a **lower case **letter.
Public static final fields (constants) are **ALL_CAPS_WITH_UNDERSCORES**.

## Add/Hide Fragment

### Add

```java

	FragmentManager fm = getFragmentManager();
	FragmentTransaction ft = fm.beginTransaction();
	mFragment1 = fm.findFragmentByTag("f1");
	if (mFragment1 == null) {
		mFragment1 = new MenuFragment();
		ft.add(mFragment1, "f1");
	}
	mFragment2 = fm.findFragmentByTag("f2");
	if (mFragment2 == null) {
		mFragment2 = new Menu2Fragment();
		ft.add(mFragment2, "f2");
	}
	ft.commit();
```

Note check if existing before add a new one.

### Hide/Show

```java

	FragmentTransaction ft = getFragmentManager().beginTransaction();
	ft.show(mFragment1);
	ft.hide(mFragment2);
	ft.commit();
```

### onRestoreInstanceState

When the system configuration changed like user's rotating, the Activity will be recreated again, such that `onRestoreInstanceState` will be called. We can change visibility of Fragment in `onRestoreInstanceState` method.

## Fragment's Menu

### Visibility

When the fragment is `show`ing, its menu will show. You can press any checkbox to test it.

### onOptionsItemSelected

Both `Activity` and `Fragment` have `onOptionsItemSelected` method. Therefore, I'm curious about who will get the select-event. I suprise me!!! You can press the `MenuItem` and look at the logcat.   

You will find:

- Everyone will get this event. Fragment1's item is pressed, and Fragment **DOES** receive the event, and so do the root Activity.
- Activity have higher priority.

Actual practice is that the fragment cover its own `MenuItem`s, instead of being caught by Activity or other Fragment. 