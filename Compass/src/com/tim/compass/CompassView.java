package com.tim.compass;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class CompassView extends View {

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
		sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
	}

	public float getBearing() {
		return bearing;
	}

}
