package com.sqisland.android.graphics_demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.sqisland.android.graphics_demo.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class PorterDuffActivity extends SpinnerImageActivity {
  protected List<Option> getOptions(Bitmap original) {
    Bitmap mask = ImageUtil.createCircle(original.getWidth(), original.getHeight());

    ArrayList<Option> options = new ArrayList<Option>();
    options.add(new Option(getString(R.string.original), original));
    options.add(new Option(getString(R.string.mask), mask));
    options.add(new Option(getString(R.string.circle_dim_around), circleDimAround(original, mask)));

    for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
      options.add(new Option(mode.toString(), drawWithPorterDuff(original, mask, mode)));
    }

    return options;
  }

  protected int getInitialSelection(int size) {
    return 2;
  }

  private Bitmap drawWithPorterDuff(Bitmap original, Bitmap mask, PorterDuff.Mode mode) {
    Bitmap bitmap = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    // Draw the original bitmap (DST)
    canvas.drawBitmap(original, 0, 0, null);

    // Draw the mask (SRC) with the specified Porter-Duff mode
    Paint maskPaint = new Paint();
    maskPaint.setXfermode(new PorterDuffXfermode(mode));
    canvas.drawBitmap(mask, 0, 0, maskPaint);

    return bitmap;
  }

  private Bitmap circleDimAround(Bitmap original, Bitmap mask) {
    Bitmap bitmap = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    // Draw the original bitmap
    canvas.drawBitmap(original, 0, 0, null);

    // DST_IN = Whatever was there, keep the part that overlaps with what I'm drawing now
    Paint maskPaint = new Paint();
    maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    canvas.drawBitmap(mask, 0, 0, maskPaint);

    // DST_OVER = Whatever was there, put it over what I'm drawing now
    Paint overPaint = new Paint();
    overPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
    overPaint.setColorFilter(createDimFilter());
    canvas.drawBitmap(original, 0, 0, overPaint);

    return bitmap;
  }

  private ColorFilter createDimFilter() {
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0f);
    float scale = 0.5f;
    colorMatrix.setScale(scale, scale, scale, 1f);
    return new ColorMatrixColorFilter(colorMatrix);
  }
}