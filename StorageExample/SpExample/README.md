This is README file fro *SharedPreference*.

## Content:

- Preface
- Where
- How
	- Use
	- Write
	- Mode 
- Example

##Preface

It's used to store primitive data.

## Where

The data [SharedPreference][SP] stored in **XML** file can be found in `shared_prefs` of application's working directory.

## How

Use belows to retrieve and hold the contents of the preferences file 'name'.

- [Context#getSharedPreferences][GSP](String name, int mode)
- [Activity#getPreferences][GP](int mode) : private to this activity.

### Use

Get [SharedPreferences][SP] and use its `get` methods.

- getAll
- getBoolean(String key, boolean defValue)
- getFloat(String key, float defValue)
- getInt(String key, int defValue)
- getLong(String key, long defValue)
- getString(String key, String defValue)
- getStringSet(String key, Set<String> defValues) 	 

Notice: these `get` methods throw [ClassCastException][CCE] if wrong type.

### Write

Use the inner interface, [Editor][SPE] ,to edit the preference using `put`, `remove` methods.

You can get [Editor][SPE] via [SharedPreferences#edit()][W1].

> All changes you make in an editor are **batched**, and not copied back to the original SharedPreferences until you call [commit()][W_commit] or [apply()][W_apply].

[W1]: http://developer.android.com/reference/android/content/SharedPreferences.html#edit()
[W_commit]:http://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()
[W_apply]:http://developer.android.com/reference/android/content/SharedPreferences.Editor.html#apply()


### Mode 

- MODE_PRIVATE
- MODE_WORLD_READABLE
- MODE_WORLD_WRITEABLE
- MODE_MULTI_PROCESS

## Example

### [SpE1][E1] : 

Test step:

1. typing *asfd* and pressing button.
2. `adb shell`
3. `su` 
4. `cd /data/data/com.tim.spe1/shared_prefs` 
5. `cat com.tim.spe1.MainActivity.xml`, 

result:
 
	<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
	<map>
    	<string name="KEY1">asfd</string>
	</map>


[E1]: SpE1


[SP]: http://developer.android.com/reference/android/content/SharedPreferences.html
[GSP]: http://developer.android.com/reference/android/content/Context.html#getSharedPreferences%28java.lang.String,%20int%29
[GP]: http://developer.android.com/reference/android/app/Activity.html#getPreferences%28int%29
[CCE]: http://developer.android.com/reference/java/lang/ClassCastException.html
[SPE]:http://developer.android.com/reference/android/content/SharedPreferences.Editor.html