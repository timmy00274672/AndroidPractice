This is note for `ListFragmentExample`.

The example is from [Gunaseelan A][s1].
## Fragment
### Dynaically add fragment

Code of `MainActivity#onCreate()`:

```java

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getFragmentManager();

		if (fm.findFragmentById(android.R.id.content) == null) {
			SimpleListFragment list = new SimpleListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}
```

Note:

- use `FragmentManager` to dynamically add/remove/replace fragment
- android.R.id.content gives you the **root element of a view**, without having to know its actual name/type/ID.

### SimpleListFragment#onCreateView

- `LayoutInflater.getContext()` : get the context for adapter 

## CustomToast

- `Window.requestFeature(int featureId)` : Enable extended screen features

	```java

		requestWindowFeature(Window.FEATURE_NO_TITLE);
	```
- `Context.getSystemService(String name)` : Return the handle to a system-level service by name

	```java

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
	```
- `Dialog.setContentView(View view)` : This view is placed directly into the screen's view hierarchy. It can itself be a complex view hierarhcy.
- `Dialog.show()` : Start the dialog and display it on screen. The window is placed in the application layer and opaque. Note that you should not override this method to do initialization when the dialog is shown, instead implement that in onStart.
- `Dialog.setCanceledOnTouchOutside(boolean cancel)`
- [Handler][H1] : 
>Scheduling messages is accomplished with the `post(Runnable)`, `postAtTime(Runnable, long)`, `postDelayed(Runnable, long)`, `sendEmptyMessage(int)`, `sendMessage(Message)`, `sendMessageAtTime(Message, long)`, and `sendMessageDelayed(Message, long)` methods. The post versions allow you to enqueue Runnable objects to be called by the message queue when they are received; the sendMessage versions allow you to enqueue a Message object containing a bundle of data that will be processed by the Handler's handleMessage(Message) method (requiring that you implement a subclass of Handler). 


[s1]:http://v4all123.blogspot.tw/2013/07/simple-listfragment-example-in-android.html
[H1]:http://developer.android.com/reference/android/os/Handler.html