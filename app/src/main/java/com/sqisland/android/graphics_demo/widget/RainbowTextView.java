package com.sqisland.android.graphics_demo.widget;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sqisland.android.graphics_demo.R;

public class RainbowTextView extends TextView {
  public RainbowTextView(Context context) {
    super(context);
  }

  public RainbowTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    int[] rainbow = {
        getResources().getColor(R.color.rainbow_red),
        getResources().getColor(R.color.rainbow_yellow),
        getResources().getColor(R.color.rainbow_green),
        getResources().getColor(R.color.rainbow_blue),
        getResources().getColor(R.color.rainbow_purple)
    };
    Shader shader = new LinearGradient(0, 0, 0, w, rainbow, null, Shader.TileMode.MIRROR);

    Matrix matrix = new Matrix();
    matrix.setRotate(90);
    shader.setLocalMatrix(matrix);

    getPaint().setShader(shader);
  }
}