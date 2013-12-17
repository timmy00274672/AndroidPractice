package com.tim.todolist3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class ToDoListItemView extends TextView {

	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;

	public ToDoListItemView(Context context, AttributeSet abs, int ds) {
		super(context, abs, ds);
		init();
	}

	public ToDoListItemView(Context context, AttributeSet abs) {
		super(context, abs);
		init();
	}

	public ToDoListItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		// Get a reference to our resource table.
		Resources myrResources = getResources();

		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myrResources.getColor(R.color.notepad_margin));
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myrResources.getColor(R.color.notepad_lines));

		// Get the paper background color and the margin width.
		paperColor = myrResources.getColor(R.color.notepad_paper);
		margin = myrResources.getDimension(R.dimen.notepad_margin);
	}

	/**
	 * use the Paint fields to draw the image.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		/*
		 * Fill the entire canvas' bitmap (restricted to the current clip) with
		 * the specified color, using srcover porterduff mode.
		 */
		canvas.drawColor(paperColor);

		/*
		 * Draw ruled lines with Canvas.drawLine(float startX, float startY,
		 * float stopX, float stopY, Paint paint
		 */
		int hight = getMeasuredHeight();
		int width = getMeasuredWidth();
		canvas.drawLine(0, 0, 0, hight, linePaint);// top line
		canvas.drawLine(0, hight, width,
				hight, linePaint);// bottom line

		// Draw margin
		canvas.drawLine(margin, 0, margin, hight, marginPaint);
		canvas.translate(margin, 0);
		
		//Use the TextView to render the text
		super.onDraw(canvas);
		canvas.restore();
	}
}
