package com.csyo.tasklist;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.csyo.tasklist.TaskList.Tasks;

/**
 * Provide the methods to insert, delete, query, and update data
 * 
 */
public class TaskListProvider extends ContentProvider {

	// database constants
	private static final String DATABASE_NAME = "tasklist.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "tasklist";
	// Conditions for query and update
	private static final int TASKS = 1;
	private static final int TASK_ID = 2;
	// URI Utility
	private static final UriMatcher sUriMatcher;
	public static final String TAG = TaskListProvider.class.getSimpleName();
	// Query fields
	private static HashMap<String, String> sTaskListProjectionMap;
	// Database helper instance
	private DbHelper mHelper;

	/**
	 * Inner utility class. Use to create/connect database and create/delete
	 * tables.
	 * 
	 */
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		/**
		 * Create table
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG,"Create table");
			String sql = "CREATE TABLE " + TaskListProvider.TABLE_NAME + " ("
					+ BaseColumns._ID + " INTEGER PRIMARY KEY,"
					+ Tasks.DATE + " TEXT,"
					+ Tasks.TIME + " TEXT,"
					+ Tasks.CONTENT + " TEXT,"
					+ Tasks.ON_OFF + " INTEGER,"
					+ Tasks.ALARM + " INTEGER,"
					+ Tasks.CREATED + " TEXT" + ");";
			db.execSQL(sql);
			Log.d(TAG,"executed SQL:"+sql);
		}

		/**
		 * Delete table
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}

	}

	/**
	 * Create or open database
	 */
	@Override
	public boolean onCreate() {
		Log.d(TAG,"Create DB");
		mHelper = new DbHelper(getContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "start querying");
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case TASKS: // Query all
			Log.d(TAG,"Query all");
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			break;
		case TASK_ID: // Query by ID
			String _id = uri.getPathSegments().get(1);
			Log.d(TAG,"Query by ID="+_id);
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			qb.appendWhere(Tasks._ID + "="
					+ _id);
			break;
		default:
			throw new IllegalArgumentException("URI Error! " + uri);
		}

		// Use default sort order
		String orederBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orederBy = TaskList.Tasks.DEFAULT_SORT_ORDER;
		} else {
			orederBy = sortOrder;
		}

		// Start querying and return cursor
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orederBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		Log.d(TAG,"result: "+c.getCount());
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case TASKS:
			return Tasks.CONTENT_TYPE;
		case TASK_ID:
			return Tasks.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initValues) {
		Log.d(TAG,"inserting data URI="+uri);
		if (sUriMatcher.match(uri) != TASKS) {
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}
		ContentValues values;
		values = initValues != null ? new ContentValues(initValues)
				: new ContentValues();

		// Start inserting and return inserted row ID
		SQLiteDatabase db = mHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, Tasks.CONTENT, values);
		if (rowId > 0) {
			Log.d(TAG,"success inserting data ID="+rowId);
			Uri taskUri = ContentUris.withAppendedId(
					TaskList.Tasks.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(taskUri, null);
			return taskUri;
		} else
			return null; // throw new SQLException("Fail to insert data " +
							// uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d(TAG,"deleting data");
		// Start deleting according to cases
		SQLiteDatabase db = mHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case TASKS: // according to specific condition
			count = db.delete(TABLE_NAME, selection, selectionArgs);
			break;
		case TASK_ID: // according to specific condition and ID
			String noteId = uri.getPathSegments().get(1);
			String where = !TextUtils.isEmpty(selection) ? " AND (" + selection
					+ ')' : "";
			count = db.delete(TABLE_NAME, BaseColumns._ID + "=" + noteId
					+ where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Wrong URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG,"updating data");
		// Start updating according to cases
		SQLiteDatabase db = mHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case TASKS:
			count = db.update(TABLE_NAME, values, selection, selectionArgs);
			break;
		case TASK_ID:
			String noteId = uri.getPathSegments().get(1);
			String where = BaseColumns._ID
					+ "="
					+ noteId
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
			count = db.update(TABLE_NAME, values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Wrong URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(TaskList.AUTHORITY, "tasklist", TASKS);
		sUriMatcher.addURI(TaskList.AUTHORITY, "tasklist/#", TASK_ID);

		sTaskListProjectionMap = new HashMap<String, String>();
		sTaskListProjectionMap.put(BaseColumns._ID, BaseColumns._ID);
		sTaskListProjectionMap.put(Tasks.CONTENT, Tasks.CONTENT);
		sTaskListProjectionMap.put(Tasks.CREATED, Tasks.CREATED);
		sTaskListProjectionMap.put(Tasks.ALARM, Tasks.ALARM);
		sTaskListProjectionMap.put(Tasks.DATE, Tasks.DATE);
		sTaskListProjectionMap.put(Tasks.TIME, Tasks.TIME);
		sTaskListProjectionMap.put(Tasks.ON_OFF, Tasks.ON_OFF);
	}
}
