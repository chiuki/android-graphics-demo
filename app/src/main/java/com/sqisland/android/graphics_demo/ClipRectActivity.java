package com.sqisland.android.graphics_demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

public class ClipRectActivity extends Activity {
  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private RectF rect = new RectF();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clip_rect);

    ImageView imageView = (ImageView) findViewById(R.id.image);
    int width = getResources().getDimensionPixelSize(R.dimen.clip_rect_width);
    int height = getResources().getDimensionPixelSize(R.dimen.clip_rect_height);

    Bitmap bitmap = createClipRect(width, height);
    imageView.setImageBitmap(bitmap);
  }

  // Assumes width > height
  private Bitmap createClipRect(int width, int height) {
    paint.setColor(Color.GREEN);

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    float radius = height / 2;

    canvas.save();
    canvas.clipRect(0, 0, width, height);

    rect.set(0, 0, width + height, height);
    canvas.drawRoundRect(rect, radius, radius, paint);

    canvas.restore();

    return bitmap;
  }
}