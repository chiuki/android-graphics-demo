package com.sqisland.android.graphics_demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FourColorsImageView extends ImageView {
  private Bitmap bitmap = null;

  public FourColorsImageView(Context context) {
    super(context);
  }

  public FourColorsImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FourColorsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (bitmap == null) {
      Bitmap quarter = Bitmap.createBitmap(getWidth()/2, getHeight()/2, Bitmap.Config.ARGB_8888);
      Canvas quarterCanvas = new Canvas(quarter);
      quarterCanvas.scale(0.5f, 0.5f);
      super.onDraw(quarterCanvas);
      quarterCanvas.scale(2, 2);

      createBitmap(quarter);
      quarter.recycle();
    }
    canvas.drawBitmap(bitmap, 0, 0, null);
  }

  private void createBitmap(Bitmap quarter) {
    bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    Paint paint = new Paint();

    // Top left
    paint.setColorFilter(new LightingColorFilter(Color.RED, 0));
    canvas.drawBitmap(quarter, 0, 0, paint);

    // Top right
    paint.setColorFilter(new LightingColorFilter(Color.YELLOW, 0));
    canvas.drawBitmap(quarter, getWidth() / 2, 0, paint);

    // Bottom left
    paint.setColorFilter(new LightingColorFilter(Color.BLUE, 0));
    canvas.drawBitmap(quarter, 0, getHeight()/2, paint);

    // Bottom right
    paint.setColorFilter(new LightingColorFilter(Color.GREEN, 0));
    canvas.drawBitmap(quarter, getWidth() / 2, getHeight() / 2, paint);
  }

  private ColorFilter createColorFilter(float red, float green, float blue) {
    float alpha = 1f;
    float[] transform =
        { red, 0, 0, 0, 0,
            0, green, 0, 0, 0,
            0, 0, blue, 0, 0,
            0, 0, 0, alpha, 0 };
    return new ColorMatrixColorFilter(transform);
  }
}