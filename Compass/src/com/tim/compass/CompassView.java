package com.tim.compass;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class CompassView extends View {

	private static final String TAG = CompassView.class.getSimpleName();
	private float bearing;
	private Paint markerPaint;
	private Paint textPaint;
	private Paint circlePaint;
	private String northString;
	private String eastString;
	private String southString;
	private String westString;
	private int textHeight;

	private void init() {
		setFocusable(true);

		Resources resources = this.getResources();
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(resources.getColor(R.color.background_color));
		circlePaint.setStrokeWidth(1);
		circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		northString = resources.getString(R.string.cardinal_north);
		eastString = resources.getString(R.string.cardinal_east);
		southString = resources.getString(R.string.cardinal_south);
		westString = resources.getString(R.string.cardinal_west);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(resources.getColor(R.color.text_color));
		textHeight = (int) textPaint.measureText("yY");

		markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		markerPaint.setColor(resources.getColor(R.color.marker_color));
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 */
	public CompassView(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int mMeasuredWidth = getMeasuredWidth();
		int mMeasuredHeight = getMeasuredHeight();
		int px = mMeasuredWidth / 2;
		int py = mMeasuredHeight / 2;
		int radius = Math.min(px, py);
		canvas.drawCircle(px, py, radius, circlePaint);
		// Rotate our perspective so that the ¡¥top¡¦ is
		// facing the current bearing.
		canvas.save();
		canvas.rotate(-bearing, px, py);
		int textWidth = (int) textPaint.measureText("w");
		int cardinalX = px - textWidth / 2;
		int cardinalY = py - radius + textHeight;
		// Draw the marker every 15 degrees and text every 45.
		for (int i = 0; i < 24; i++) {
			// Draw a marker.
			canvas.drawLine(px, py - radius, px, py - radius + 10, markerPaint);
			canvas.save();
			canvas.translate(0, textHeight);
			// Draw the cardinal points
			if (i % 6 == 0) {
				String dirString = "";
				switch (i) {
				case (0): {
					dirString = northString;
					int arrowY = 2 * textHeight;
					canvas.drawLine(px, arrowY, px - 5, 3 * textHeight,
							markerPaint);
					canvas.drawLine(px, arrowY, px + 5, 3 * textHeight,
							markerPaint);
					break;
				}
				case (6):
					dirString = eastString;
					break;
				case (12):
					dirString = southString;
					break;
				case (18):
					dirString = westString;
					break;
				}
				canvas.drawText(dirString, cardinalX, cardinalY, textPaint);
			} else if (i % 3 == 0) {
				// Draw the text every alternate 45deg
				String angle = String.valueOf(i * 15);
				float angleTextWidth = textPaint.measureText(angle);
				int angleTextX = (int) (px - angleTextWidth / 2);
				int angleTextY = py - radius + textHeight;
				canvas.drawText(angle, angleTextX, angleTextY, textPaint);
			}
			canvas.restore();
			canvas.rotate(15, px, py);
		}
		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int min = Math.min(measure(widthMeasureSpec),
				measure(heightMeasureSpec));
		setMeasuredDimension(min, min);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
		Log.d(TAG, String.format(
				"in dispatchPopulateAccessibilityEvent method, type = %d",
				event.getEventType()));
		super.dispatchPopulateAccessibilityEvent(event);
		if (isShown()) {
			String bearingStr = String.valueOf(bearing);
			if (bearingStr.length() > AccessibilityEvent.MAX_TEXT_LENGTH)
				bearingStr = bearingStr.substring(0,
						AccessibilityEvent.MAX_TEXT_LENGTH);
			event.getText().add(bearingStr);
			return true;
		} else
			return false;

	}

	private int measure(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			result = 200;
		} else {
			result = specSize;
		}
		return result;
	}

	public void setBearing(float _bearing) {
		bearing = _bearing;
		Log.d(TAG,String.format("setBearing : %s", _bearing));
		sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
		// this.invalidate();
	}

	public float getBearing() {
		return bearing;
	}

	@Override
	public AccessibilityNodeInfo createAccessibilityNodeInfo() {
		Log.d(TAG, "AccessibilityNodeInfo");
		return super.createAccessibilityNodeInfo();
	}

	@Override
	protected void dispatchSetPressed(boolean pressed) {
		Log.d(TAG, "dispatchSetPressed");
		super.dispatchSetPressed(pressed);
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		Log.d(TAG, "dispatchUnhandledMove");
		return super.dispatchUnhandledMove(focused, direction);
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		Log.d(TAG, "dispatchKeyEventPreIme");
		return super.dispatchKeyEventPreIme(event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.d(TAG, "dispatchKeyEvent");
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		Log.d(TAG, "dispatchKeyShortcutEvent");
		return super.dispatchKeyShortcutEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean dispatchTrackballEvent(MotionEvent event) {
		Log.d(TAG, "dispatchTrackballEvent");
		return super.dispatchTrackballEvent(event);
	}

	@Override
	public boolean dispatchGenericMotionEvent(MotionEvent event) {
		Log.d(TAG, "dispatchGenericMotionEvent");
		return super.dispatchGenericMotionEvent(event);
	}

	@Override
	protected boolean dispatchHoverEvent(MotionEvent event) {
		Log.d(TAG, "dispatchHoverEvent");
		return super.dispatchHoverEvent(event);
	}

	@Override
	protected boolean dispatchGenericPointerEvent(MotionEvent event) {
		Log.d(TAG, "dispatchGenericPointerEvent");
		return super.dispatchGenericPointerEvent(event);
	}

	@Override
	protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
		Log.d(TAG, "dispatchGenericFocusedEvent");
		return super.dispatchGenericFocusedEvent(event);
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		Log.d(TAG, "dispatchWindowFocusChanged");
		super.dispatchWindowFocusChanged(hasFocus);
	}

	@Override
	protected void dispatchVisibilityChanged(View changedView, int visibility) {
		Log.d(TAG, "dispatchVisibilityChanged");
		super.dispatchVisibilityChanged(changedView, visibility);
	}

	@Override
	public void dispatchDisplayHint(int hint) {
		Log.d(TAG, "dispatchDisplayHint");
		super.dispatchDisplayHint(hint);
	}

	@Override
	public void dispatchWindowVisibilityChanged(int visibility) {
		Log.d(TAG, "dispatchWindowVisibilityChanged");
		super.dispatchWindowVisibilityChanged(visibility);
	}

	@Override
	public void dispatchConfigurationChanged(Configuration newConfig) {
		Log.d(TAG, "dispatchConfigurationChanged");
		super.dispatchConfigurationChanged(newConfig);
	}

	@Override
	public void createContextMenu(ContextMenu menu) {
		Log.d(TAG, "createContextMenu");
		super.createContextMenu(menu);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		Log.d(TAG, "dispatchDraw");
		super.dispatchDraw(canvas);
	}

	@Override
	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
		Log.d(TAG, "dispatchSaveInstanceState");
		super.dispatchSaveInstanceState(container);
	}

	@Override
	protected void dispatchRestoreInstanceState(
			SparseArray<Parcelable> container) {
		Log.d(TAG, "dispatchRestoreInstanceState");
		super.dispatchRestoreInstanceState(container);
	}

	@Override
	public void destroyDrawingCache() {
		Log.d(TAG, "destroyDrawingCache");
		super.destroyDrawingCache();
	}

	@Override
	protected void dispatchSetSelected(boolean selected) {
		Log.d(TAG, "dispatchSetSelected");
		super.dispatchSetSelected(selected);
	}

	@Override
	protected void dispatchSetActivated(boolean activated) {
		Log.d(TAG, "dispatchSetActivated");
		super.dispatchSetActivated(activated);
	}

	@Override
	public void dispatchWindowSystemUiVisiblityChanged(int visible) {
		Log.d(TAG, "dispatchWindowSystemUiVisiblityChanged");
		super.dispatchWindowSystemUiVisiblityChanged(visible);
	}

	@Override
	public void dispatchSystemUiVisibilityChanged(int visibility) {
		Log.d(TAG, "dispatchSystemUiVisibilityChanged");
		super.dispatchSystemUiVisibilityChanged(visibility);
	}

	@Override
	public boolean dispatchDragEvent(DragEvent event) {
		Log.d(TAG, "dispatchDragEvent");
		return super.dispatchDragEvent(event);
	}

}
