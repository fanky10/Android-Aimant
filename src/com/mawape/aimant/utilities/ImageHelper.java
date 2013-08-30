package com.mawape.aimant.utilities;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageHelper {

//	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float deviceDensity) {
//		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//				bitmap.getHeight(), Config.ARGB_8888);
//		Canvas canvas = new Canvas(output);
//
//		final int color = 0xff424242;
//		final Paint paint = new Paint();
//		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		final RectF rectF = new RectF(rect);
//		final float roundPx = 3 * deviceDensity;
//
//		paint.setAntiAlias(true);
//		canvas.drawARGB(0, 0, 0, 0);
//		paint.setColor(color);
//		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, rect, rect, paint);
//
//		return output;
//	}

	public static Bitmap getRoundedTopCornerBitmap(Bitmap bitmap, float roundPx, int color) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
//		final Rect topRightRect = new Rect(bitmap.getWidth()/2, 0,
//				bitmap.getWidth(), bitmap.getHeight() / 2);
//		final Rect topLeftRect = new Rect(0,bitmap.getWidth()/2,
//				bitmap.getWidth(), bitmap.getHeight() / 2);
		final Rect bottomRect = new Rect(0, bitmap.getHeight() / 2,
				bitmap.getWidth(), bitmap.getHeight());
		

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		// Fill in upper right corner
//		canvas.drawRect(topRightRect, paint);
		// Fill in bottom corners
		canvas.drawRect(bottomRect, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}
