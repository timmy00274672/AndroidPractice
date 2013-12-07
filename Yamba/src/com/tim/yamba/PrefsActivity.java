package com.tim.yamba;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * 	Why and how use this:
 * 	<ul>
 * 		<li>It's used to show the preference window(activity)</li>
 * 		<li>It need to be registered in <b>androidManifest</b> </li>
 * 		<li>It's not the main activity,  so it's not reachable. We use intent to do so. </li> 
 * 	</ul>{@link StatusActivity#onOptionsItemSelected(android.view.MenuItem)}
 * @author Tim
 *
 */
public class PrefsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}
