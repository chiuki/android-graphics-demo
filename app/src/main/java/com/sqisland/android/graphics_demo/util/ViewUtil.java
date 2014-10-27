package com.sqisland.android.graphics_demo.util;

import android.annotation.TargetApi;
import android.view.View;

public abstract class ViewUtil {
  @TargetApi(11)
  public static void setSoftwareLayerType(View view) {
    view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
  }
}
