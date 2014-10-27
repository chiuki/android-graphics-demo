package com.sqisland.android.graphics_demo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;

import java.util.Arrays;
import java.util.List;

@TargetApi(17)
public class RenderscriptConvolutionActivity extends SpinnerImageActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    imageView.setBackgroundColor(Color.BLACK);  // for edge detection
  }

  @Override
  protected List<Option> getOptions(Bitmap original) {
    return Arrays.asList(
        new Option(getString(R.string.original), original),
        createOption(original, R.string.sharpen, new float[] {
            0, -1, 0,
            -1, 5, -1,
            0, -1, 0
        }),
        createOption(original, R.string.edge_detection, new float[] {
            -1, -1, -1,
            -1,  8, -1,
            -1, -1, -1
        }),
        createOption(original, R.string.box_blur, new float[] {
            1, 1, 1,
            1, 1, 1,
            1, 1, 1
        }, 9),
        createOption(original, R.string.fuzzy_glass, new float[] {
            0,  20,  0,
           20, -59, 20,
            1,  13,  0
        }, 7)
    );
  }

  private Option createOption(Bitmap original, int titleId, float[] coefficients) {
    return createOption(original, titleId, coefficients, 1);
  }

  private Option createOption(Bitmap original, int titleId, float[] coefficients, float divisor) {
    if (divisor != 0 && divisor != 1) {
      for (int i = 0; i < coefficients.length; ++i) {
        coefficients[i] = coefficients[i] / divisor;
      }
    }
    String title = getString(titleId);
    Bitmap bitmap = convolve(original, coefficients);
    return new Option(title, bitmap);
  }

  private Bitmap convolve(Bitmap original, float[] coefficients) {
    Bitmap bitmap = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(),
        Bitmap.Config.ARGB_8888);

    RenderScript rs = RenderScript.create(this);

    Allocation allocIn = Allocation.createFromBitmap(rs, original);
    Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);

    ScriptIntrinsicConvolve3x3 convolution
        = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
    convolution.setInput(allocIn);
    convolution.setCoefficients(coefficients);
    convolution.forEach(allocOut);

    allocOut.copyTo(bitmap);

    rs.destroy();

    return bitmap;
  }
}