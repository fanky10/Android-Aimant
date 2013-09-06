package com.mawape.aimant.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * esta clase hace que el width sea al 100% y el width flexible al aspect ratio
 * para que ande: android:scaleType="centerCrop" en el component
 * 
 * @author 
 *         http://stackoverflow.com/questions/13992535/android-imageview-scale-smaller
 *         -image-to-width-with-flexible-height-without-crop
 * 
 */
public class DynamicImageView extends ImageView {

	public DynamicImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final Drawable d = this.getDrawable();

		if (d != null) {
			// ceil not round - avoid thin vertical gaps along the left/right
			// edges
			final int width = MeasureSpec.getSize(widthMeasureSpec);
			final int height = (int) Math.ceil(width
					* (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());
			this.setMeasuredDimension(width, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}