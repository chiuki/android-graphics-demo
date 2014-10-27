package com.sqisland.android.graphics_demo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.util.ArrayList;
import java.util.List;

@TargetApi(17)
public class RenderscriptBlurActivity extends SpinnerImageActivity {
  @Override
  protected List<Option> getOptions(Bitmap original) {
    ArrayList<Option> options = new ArrayList<Option>();
    options.add(new Option(getString(R.string.original), original));

    for (float radius : new float[]{1, 4, 9, 16, 25}) {
      options.add(createOption(original, radius));
    }

    return options;
  }

  protected int getInitialSelection(int size) {
    return size - 1;
  }

  private Option createOption(Bitmap original, float radius) {
    String title = getString(R.string.blur_with_radius, radius);
    Bitmap bitmap = blur(original, radius);
    return new Option(title, bitmap);
  }

  private Bitmap blur(Bitmap original, float radius) {
    Bitmap bitmap = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(),
        Bitmap.Config.ARGB_8888);

    RenderScript rs = RenderScript.create(this);

    Allocation allocIn = Allocation.createFromBitmap(rs, original);
    Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);

    ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    blur.setInput(allocIn);
    blur.setRadius(radius);
    blur.forEach(allocOut);

    allocOut.copyTo(bitmap);

    rs.destroy();

    return bitmap;
  }
}