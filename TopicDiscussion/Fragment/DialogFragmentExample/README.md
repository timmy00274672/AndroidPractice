This is note for `DialogFragmentExample`.


The simplest use of DialogFragment is as a floating container for the fragment's view hierarchy. 

## Lifecycle

DialogFragment does various things to keep the fragment's lifecycle driving it, instead of the Dialog. Note that dialogs are generally autonomous entities -- they are their **own** window, receiving their own input events, and often deciding on their **own when to disappear** (by receiving a back key event or the user clicking on a button).

DialogFragment needs to ensure that what is happening with the Fragment and Dialog states remains consistent. To do this, it watches for dismiss events from the dialog and takes care of removing its own state when they happen. This means you should use [show(FragmentManager, String)][show1] or [show(FragmentTransaction, String)][show2] to add an instance of DialogFragment to your UI, as these keep track of how DialogFragment should remove itself when the dialog is dismissed. 

Here is the example, [MainActivity][Style/MA]:

```java

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
```
## DialogFragment view

### style

`DialogFragment.setStyle(int style, int theme)` : Calling this after the fragment's Dialog is created will have no effect. 

I use this method in [MyCustomDialog#onCreate][Style/MCD], which is called before creating the dialog.

### onCreateDialog

Override to build your own custom Dialog container. This is typically used to show an `AlertDialog` instead of a generic Dialog; when doing so, onCreateView(LayoutInflater, ViewGroup, Bundle) does not need to be implemented since the AlertDialog takes care of its own content. 

This method will be called after `onCreate(Bundle)` and before `onCreateView(LayoutInflater, ViewGroup, Bundle)`. *The default implementation simply instantiates and returns a Dialog class*. 

Note: DialogFragment own the `Dialog.setOnCancelListener` and `Dialog.setOnDismissListener` callbacks. You must **not** set them yourself. To find out about these events, override `onCancel(DialogInterface)` and `onDismiss(DialogInterface)`.



- `Window.setFlags(int flags, int mask)` : Note that some flags must be set before the window decoration is created 

	```java

		int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		dialog.getWindow().setFlags(flagFullscreen, flagFullscreen);
		dialog.setContentView(R.layout.custom_dialog);
	```

[show1]:http://developer.android.com/reference/android/app/DialogFragment.html#show(android.app.FragmentManager,%20java.lang.String)
[show2]:http://developer.android.com/reference/android/app/DialogFragment.html#show(android.app.FragmentTransaction,%20java.lang.String)
[style/src]:style/src\com\tim\dialogfragmentexample
[Style/MA]:style/src\com\tim\dialogfragmentexample/MainActivity.java
[Style/MCD]:style/src\com\tim\dialogfragmentexample/MyCustomDialog.java