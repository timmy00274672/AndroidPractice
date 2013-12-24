I use [ContextWrapper#openFileOutput](http://developer.android.com/reference/android/content/ContextWrapper.html#openFileOutput%28java.lang.String,%20int%29) to get `FileOutputStream`. (`Activity` extends `ContextWrapper`)


```java
	try {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				openFileOutput(FILENAME, Context.MODE_PRIVATE));
		outputStreamWriter.write("HIHI");
		outputStreamWriter.close();
	} catch (IOException e) {
		Log.e(TAG, "File write failed: " + e.toString());
		}

```