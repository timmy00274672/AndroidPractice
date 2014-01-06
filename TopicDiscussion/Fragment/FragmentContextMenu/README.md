This is note for `FragmentContextMenu`. And this note can be found in wiki page-[Context-Menu][Mwiki/cm].

## Context Menu

### What is context menu
What is context menu? When we refer to the PC, context menu means the window poping out when we do right-click mouse operation.

FROM [wiki][wiki/cm]:
> 
> A context menu offers a limited set of choices that are available in the current state, or context, of the operating system or application. Usually the available choices are actions related to the selected object.
> Implementaion:
> 
> - On a computer running Microsoft Windows, Mac OS X, or Unix running the X Window System, clicking the secondary mouse button (usually the right button) opens a context menu for the region that is under the mouse pointer.
> - On systems that support one-button mice, context menus are typically opened by **pressing and holding** the primary mouse button (this works on the icons in the Dock on Mac OS X) or by pressing a keyboard/mouse button combination (e.g. Ctrl-mouse click in Mac OS). 
> 
> Usability:
> 
> Context menus have received some criticism from usability analysts when improperly used, as some applications make certain features only available in context menus, which may confuse even experienced users (especially when the context menus can only be activated in a limited area of the application's client window).
> 
> Context menus usually open in a fixed position under the pointer, but when the pointer is near a screen edge the menu will be displaced - thus reducing consistency and impeding use of muscle memory. If the context menu is being triggered by keyboard, such as by using Shift + F10, the context menu appears near the focused widget instead of the position of the pointer, to save recognition efforts.

### Android Context Menu

In android, context menu is created when you long-press some the view.

## API
###  onCreateContextMenu 

`public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)`

- Called when a context menu for the view is about to be shown. 
- Unlike `onCreateOptionsMenu(Menu, MenuInflater)`, this will be called **every time** the context menu is about to be shown and should be populated for the view
- Use `onContextItemSelected(android.view.MenuItem)` to know when an item has been selected.
- parameter `menuInfo` : Extra information about the item for which the context menu should be shown. This information will vary depending on the class of parameter `v`. 
- It is not safe to hold onto the context menu after this method returns. Called when the context menu for this view is being built. It is not safe to hold onto the menu after this method returns.

### onContextItemSelected

`public boolean onContextItemSelected (MenuItem item)`

- This hook is called whenever an item in a context menu is selected. 
- Return false to allow normal context menu processing to proceed, true to consume it here. 

### ContextMenu.ContextMenuInfo

Additional information regarding the creation of the context menu. For example, `AdapterView`s use this to pass the exact item position within the adapter that initiated the context menu. 

For [example][ex1], detecting which selected item (in a ListView) spawned the ContextMenu (Android):

```java

	@Override
	public void onCreateContextMenu(ContextMenu contextMenu,
	                                View v,
	                                ContextMenu.ContextMenuInfo menuInfo) {
	    AdapterView.AdapterContextMenuInfo info =
	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    selectedWord = ((TextView) info.targetView).getText().toString();
	    selectedWordId = info.id;
	
	    contextMenu.setHeaderTitle(selectedWord);
	    contextMenu.add(0, CONTEXT_MENU_EDIT_ITEM, 0, R.string.edit);
	    contextMenu.add(0, CONTEXT_MENU_DELETE_ITEM, 1, R.string.delete);
	}
```

### registerForContextMenu

Registers a context menu to be shown for the given view (multiple views can show the context menu). This method will set the `View.OnCreateContextMenuListener` on the view to this fragment, so `onCreateContextMenu(ContextMenu, View, ContextMenuInfo)` will be called when it is time to show the context menu.

### unregisterForContextMenu

[wiki/cm]:http://en.wikipedia.org/wiki/Context_menu
[ex1]:http://stackoverflow.com/questions/2321332/detecting-which-selected-item-in-a-listview-spawned-the-contextmenu-android
[Mwiki/cm]:https://github.com/timmy00274672/AndroidPractice/wiki/Context-Menu