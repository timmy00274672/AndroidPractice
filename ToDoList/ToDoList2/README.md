In this version, use **fragment** to our To-Do List project.

----
Last time we use one EditText and one ListView. Now we seperate to two fragments.

- [NewItemFragment](src/com/tim/todolist2/NewItemFragment.java)
	
	It inflates the layout, [new_item_fragment](res/layout/new_item_fragment.xml).
	
- [ToDoListFragment](src/com/tim/todolist2/ToDoListFragment.java)

	It extends ListFragment, including a default UI consisting of a single List View, which is sufficient for this example. You can easily customize the default List Fragment UI by creating your own custom layout and inflating it within the onCreateViewhandler. Any custom layout must include a List View node with the ID specified as @android:id/list.

----

As the wiki page [Fragments](https://github.com/timmy00274672/AndroidPractice/wiki/Fragments#pratical-issue) says:

>	Where your Fragment needs to share events with its host Activity (such as signaling UI selections), it's good practice to create a callback interface within the Fragment that a host Activity must implement.
	
[NewItemFragment](src/com/tim/todolist2/NewItemFragment.java) need a callback interface, used to notify the parent Activity that editText needs to be added. Therefore, I add a callback inner interface `OnNewItemAddedListener`.

```java
public interface OnNewItemAddedListener {
	public void onNewItemAdded(String newItem);
}
```

We need to make sure that the associated (attach) Activity implments the interface. I override `onAttach`:

```java
 /**
 * Here will check that the activity using this fragment extending
 * {@link OnNewItemAddedListener}. If not, this method will throw a
 * ClassCastExcetion
 */
@Override
public void onAttach(Activity activity) {
	super.onAttach(activity);
	try {
		onNewItemAddedListener = (OnNewItemAddedListener) activity;
	} catch (ClassCastException e) {
		throw new ClassCastException("must implement "
				+ IMPLEMENT_INTERFACE);
	}
}
``` 
----
 
Update the [main.xml](res/layout/main.xml)
	
Use `androidLname` to specify which class (extending Fragment) show there.

----

Other details are in comment, and I don't repeat here.
