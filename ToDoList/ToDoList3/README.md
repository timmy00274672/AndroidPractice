Outline:

- [Overview](README.md#overview)
- [Custom View](README.md#custom-view)
- [Canvas](README.md#canvas)
- [Paint](README.md#paint)
- [View](README.md#view)
- [Resource](README.md#resource)

----

### Overview 
In this version, I change the the layout file, which is bound by the adapter, to a custom one.

Compare the differece in [ToDoListActivity](src/com/tim/todolist3/ToDoListActivity.java).

The older one:

```java
arrayAdapter = new ArrayAdapter<String>(this,
	android.R.layout.simple_list_item_1, arrayList);
```

The new one:

```java
arrayAdapter = new ArrayAdapter<String>(this,
	R.layout.todolist_item, arrayList);
```

----
### Custom View

Here I create a custom view [ToDoListItemView](src/com/tim/todolist3/ToDoListItemView.java) which extends `TextView`.

I want to override the original appearance, so I override the `onDraw(Canvas canvas)` method. Therefore, we need some basic knowledge of **canvas**.

### Canvas

Methods used in this version([Canvas](http://developer.android.com/reference/android/graphics/Canvas.html)):

- `drawColor(int color)` : Fill the entire canvas' bitmap (restricted to the current clip) with the specified color, using srcover porterduff mode.
- `drawLine(float startX, float startY, float stopX, float stopY, Paint paint)`: Draw a line segment with the specified start and stop x,y coordinates, using the specified paint. NOTE: since a line is always "framed", the Style is ignored in the paint.
- `translate(float dx, float dy)` : Preconcat the current matrix with the specified translation
- `restore()` : This call balances a previous call to save(), and is used to remove all modifications to the matrix/clip state since the last save call. It is an error to call restore() more times than save() was called.

### Paint

Methods used in this version([Paint](http://developer.android.com/reference/android/graphics/Paint.html)):

- `Paint(int flags)` : Create a new paint with the specified flags. 
	- `Paint.ANTI_ALIAS_FLAG` : bit mask for the flag enabling antialiasing
	![antialiasing](doc-img/with-without-antialiasing.gif)
- `setColor(int color)` : Set the paint's color. Note that the color is an int containing alpha as well as r,g,b. This 32bit value is not premultiplied, meaning that its alpha can be any value, regardless of the values of r,g,b. See the Color class for more details. Use [Resources.getColor(int id)](http://developer.android.com/reference/android/content/res/Resources.html#getColor%28int%29), discussed later, to get the value.

### View

Methods used in this version([View](http://developer.android.com/reference/android/view/View.html)):

- `getResources()` : Returns the resources associated with this view.

### Resource

Class for **accessing an application's resources**. This sits on top of the asset manager of the application (accessible through getAssets) and provides a high-level API for getting typed data from the assets. 

The Android resource system keeps track of all non-code assets associated with an application. You can use this class to access your application's resources. You can generally acquire the android.content.res.Resources instance associated with your application with getResources().

The Android SDK tools compile your application's resources into the application binary at build time. To use a resource, you must install it correctly in the source tree (inside your project's res/ directory) and build your application. As part of the build process, the SDK tools generate symbols for each resource, which you can use in your application code to access the resources.

Using application resources makes it easy to update various characteristics of your application without modifying code, and—by providing sets of alternative resources—enables you to optimize your application for a variety of device configurations (such as for different languages and screen sizes). This is an important aspect of developing Android applications that are compatible on different types of devices.


