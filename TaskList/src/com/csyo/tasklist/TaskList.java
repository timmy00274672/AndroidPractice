package com.csyo.tasklist;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Constants needed for the database
 * 
 * 
 */
public final class TaskList {

	/**
	 * Authority constant
	 */
	public static final String AUTHORITY = "com.csyo.tasklist.provider.TaskList";

	private TaskList() {
	}

	public static final class Tasks implements BaseColumns {

		private Tasks() {
		}

		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/tasklist");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.csyo.tasklist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.csyo.tasklist";
		
		public static final String DEFAULT_SORT_ORDER = "created DESC";
		public static final String CONTENT = "content";
		public static final String CREATED = "created";
		public static final String DATE = "date";
		public static final String TIME = "time";
		public static final String ON_OFF = "on_off";
		public static final String ALARM = "alarm";
	}

}
