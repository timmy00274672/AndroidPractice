In this version, I demo the most basic knowledge you must know.

1. How to read and write [AndroidManifest.xml](AndroidManifest.xml)
	
    I register one Activity as a node in application node. When the application start, the activity will be launched automatically, so we use the `intent-filter` like this way. In wiki, there are more details about manifest file.
    ```xml
   	 <activity
    	android:name="com.tim.todolist.ToDoListActivity"
    	android:label="@string/app_name" >
    	
        <intent-filter>
    		<action android:name="android.intent.action.MAIN" />
    		<category android:name="android.intent.category.LAUNCHER" />
    	</intent-filter>
    </activity>
    ```
1. Notice above manifest file, 
	```xml
    android:label="@string/app_name"
    ``` 
    above code use the external resource. 
	
    You can find the resource in [res\values\string.xml](res/values/strings.xml), and the corresponding code is: 
    ```xml	
    <string name="app_name">ToDoList</string>
	``` 
    which means that the frist one is equal to 
    ```xml
    android:label="ToDoList"
    ```
    
1.  Now we know that the first loaded activity is
	"com.tim.todolist.ToDoListActivity". 
    You can find the source code in [src](src/com/tim/todolist/ToDoListActivity.java).
    
	In the class, I demo
    - override `Activity`, and it's method `onCreate()`
    - `setContentView` method
    - get the reference of view via `findViewById`
    - the constructor of `ArrayAdapter`
    - bind the view(`ListView`) to the data(here, `ArrayList<String>`)
    - register an Listener with inline class to widget(here, `EditText`)
    
    And notice:
    - R is auto-generated file.
    - More details can be found in the comments.
    - More details on lifecycle can be found in our wiki page.
1.  In [ToDoListActivity](src/com/tim/todolist/ToDoListActivity.java) , `setContentView(R.layout.main);` 
	**inflate** the xml file to a view object. 
    The file you can found in `src/layout/`. 
    It's readable, and the details can be found in corresponding wiki page,
    so I don't repeat here.
    