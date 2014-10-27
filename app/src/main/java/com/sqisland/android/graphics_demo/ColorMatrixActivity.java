package com.sqisland.android.graphics_demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import java.util.Arrays;
import java.util.List;

public class ColorMatrixActivity extends SpinnerImageActivity {
  protected List<Option> getOptions(Bitmap original) {
    return Arrays.asList(
        new Option(getString(R.string.original), original),
        createOption(original, R.string.grayscale, getGrayscaleColorMatrix()),
        createOption(original, R.string.sepia, getSepiaColorMatrix()),
        createOption(original, R.string.binary, getBinaryColorMatrix()),
        createOption(original, R.string.invert, new ColorMatrix(new float[]{
            -1,  0,  0,  0, 255,
             0, -1,  0,  0, 255,
             0,  0, -1,  0, 255,
             0,  0,  0,  1,   0
        })),
        createOption(original, R.string.alpha_blue, new ColorMatrix(new float[]{
               0,    0,    0, 0,   0,
            0.3f,    0,    0, 0,  50,
               0,    0,    0, 0, 255,
            0.2f, 0.4f, 0.4f, 0, -30
        })),
        createOption(original, R.string.alpha_pink, new ColorMatrix(new float[]{
               0,    0,    0, 0, 255,
               0,    0,    0, 0,   0,
            0.2f,    0,    0, 0,  50,
            0.2f, 0.2f, 0.2f, 0, -20
        }))
    );
  }

  private Option createOption(Bitmap original, int titleId, ColorMatrix colorMatrix) {
    String title = getString(titleId);
    Bitmap bitmap = process(original, colorMatrix);
    return new Option(title, bitmap);
  }

  protected Bitmap process(Bitmap original, ColorMatrix colorMatrix) {
    Bitmap bitmap = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    Paint paint = new Paint();
    paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    canvas.drawBitmap(original, 0, 0, paint);

    return bitmap;
  }

  private ColorMatrix getGrayscaleColorMatrix() {
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0);
    return colorMatrix;
  }

  private ColorMatrix getSepiaColorMatrix() {
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0);

    ColorMatrix colorScale = new ColorMatrix();
    colorScale.setScale(1, 1, 0.8f, 1);

    // Convert to grayscale, then apply brown color
    colorMatrix.postConcat(colorScale);

    return colorMatrix;
  }

  private ColorMatrix getBinaryColorMatrix() {
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0);

    float m = 255f;
    float t = -255*128f;
    ColorMatrix threshold = new ColorMatrix(new float[] {
        m, 0, 0, 1, t,
        0, m, 0, 1, t,
        0, 0, m, 1, t,
        0, 0, 0, 1, 0
    });

    // Convert to grayscale, then threshold
    colorMatrix.postConcat(threshold);

    return colorMatrix;
  }
}