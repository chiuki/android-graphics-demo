package com.sqisland.android.graphics_demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.sqisland.android.graphics_demo.R;

// Inspired by http://www.curious-creature.org/2012/12/13/android-recipe-2-fun-with-shaders/
public class PeekThroughImageView extends ImageView {
  private final float radius;
  private Paint paint = null;

  private float x;
  private float y;
  private boolean shouldDrawOpening = false;

  public PeekThroughImageView(Context context) {
    super(context);
    radius = context.getResources().getDimensionPixelSize(R.dimen.peek_through_radius);
  }

  public PeekThroughImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    radius = context.getResources().getDimensionPixelSize(R.dimen.peek_through_radius);
  }

  public PeekThroughImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    radius = context.getResources().getDimensionPixelSize(R.dimen.peek_through_radius);
  }

  @Override
  public boolean onTouchEvent(MotionEvent motionEvent) {
    int action = motionEvent.getAction();
    shouldDrawOpening = (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE);
    x = motionEvent.getX();
    y = motionEvent.getY();
    invalidate();
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (paint == null) {
      Bitmap original = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      Canvas originalCanvas = new Canvas(original);
      super.onDraw(originalCanvas);

      Shader shader = new BitmapShader(original, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

      paint = new Paint();
      paint.setShader(shader);
    }

    canvas.drawColor(Color.GRAY);
    if (shouldDrawOpening) {
      canvas.drawCircle(x, y - radius, radius, paint);
    }
  }
}