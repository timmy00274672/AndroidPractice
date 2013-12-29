This is README file of *FileExample*.

## Contents

- Internal Storage
	- Use
	- Mode 
	- Example
- External Storage
	- Use
	- Limit


## Internal Storage

### Use
> You can save files directly on the device's internal storage. By default, files saved to the internal storage are private to your application and other applications cannot access them (nor can the user). When the user *uninstalls* your application, these files are *removed*.

To create and **write** a private file to the internal storage:

1. Call [openFileOutput()][OFO](String name, int **mode**) with the name of the file and the operating mode. This returns a **FileOutputStream**.
2. Write to the file with write().
3. Close the stream with **close**().

And read in similar way.

The private files are in the `files` directory within the working directory of the app.

### Methods

1. [Context#openFileOutput][OFO](String name, int **mode**)
2. [Context#openFileInput][OFI](String name) 
3. [Context#fileList()][FL]
4. [Context#deleteFile][DF](String name)

### Mode

- `MODE_APPEND`
- `MODE_PRIVATE`
- `MODE_WORLD_READABLE`
- `MODE_WORLD_WRITEABLE`

### Example


[OFO]:http://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
[FL]:http://developer.android.com/reference/android/content/Context.html#fileList()
[DF]:http://developer.android.com/reference/android/content/Context.html#deleteFile(java.lang.String)
[OFI]:http://developer.android.com/reference/android/content/Context.html#openFileInput(java.lang.String)