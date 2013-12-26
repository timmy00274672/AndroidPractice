This is the readme file for Action Bar demo

## Part 1

- Action Items defined in `res > actions.xml`
- Enable Up (Back) Navigation in non-MainActivities
	- Each activity needs configure its `onCreate()` and `android:parentActivityName` attribute in manifest
- Enable split action bar in `MainActivity`
	- `android:uiOptions` attribute in manifest

## Part 2

Implement search widget in action bar.

- Add `android:actionViewClass="android.widget.SearchView"` in search menu item and define a searchable configuration in `res > xml`
	- and declare in the manifest with `<intent-filter>` and `<meta-data>`
- Associate SearchView in `MainActivity#onCreateOptionsMenu()`
- `SearchResultsActivity` need to handle intent that is sent from search widget
	- handle both in `onCreate()` and [`onNewIntent()`](http://developer.android.com/reference/android/app/Activity.html#onNewIntent%28android.content.Intent%29)

