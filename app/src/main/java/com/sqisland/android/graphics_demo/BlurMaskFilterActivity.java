package com.sqisland.android.graphics_demo;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqisland.android.graphics_demo.util.ViewUtil;

public class BlurMaskFilterActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blur_mask_filter);

    LinearLayout container = (LinearLayout) findViewById(R.id.container);
    for (BlurMaskFilter.Blur style : BlurMaskFilter.Blur.values()) {
      TextView textView = new TextView(this);
      textView.setTextAppearance(this, R.style.TextAppearance_Huge_Green);
      applyFilter(textView, style);

      LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
      params.gravity = Gravity.CENTER;
      container.addView(textView, params);
    }
  }

  private void applyFilter(
      TextView textView, BlurMaskFilter.Blur style) {
    if (Build.VERSION.SDK_INT >= 11) {
      ViewUtil.setSoftwareLayerType(textView);
    }
    textView.setText(style.name());
    BlurMaskFilter filter = new BlurMaskFilter(textView.getTextSize() / 10, style);
    textView.getPaint().setMaskFilter(filter);
  }
}