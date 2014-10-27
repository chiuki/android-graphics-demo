package com.sqisland.android.graphics_demo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class ImageUtil {
  public static Bitmap createCircle(int width, int height) {
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.BLUE);

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    float radius = Math.min(width, height) * 0.45f;
    canvas.drawCircle(width / 2, height / 2, radius, paint);

    return bitmap;
  }
}