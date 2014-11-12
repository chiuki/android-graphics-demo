package com.sqisland.android.graphics_demo;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class HollowTextActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hollow_text);

    final TextView textView = (TextView) findViewById(R.id.text);

    int strokeWidth =
        getResources().getDimensionPixelSize(R.dimen.dashed_text_stroke_width);
    final HollowSpan span = new HollowSpan(strokeWidth);

    String text = textView.getText().toString();
    final SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(span, 0, text.length(), 0);

    final Spinner spinner = (Spinner) findViewById(R.id.spinner);
    final List<Option> options = Arrays.asList(
        new Option(getString(R.string.solid), null),
        new Option(getString(R.string.dash), getDashPathEffect(strokeWidth)),
        new Option(getString(R.string.round_dash), getCornerDashPathEffect(strokeWidth)),
        new Option(getString(R.string.triangle), getTrianglePathEffect(strokeWidth))
    );
    ArrayAdapter<Option> adapter = new ArrayAdapter<Option>(
        this, android.R.layout.simple_spinner_item, options);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Option option = options.get(position);
        span.setPathEffect(option.effect);
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  private PathEffect getDashPathEffect(int strokeWidth) {
    return new DashPathEffect(new float[] { strokeWidth * 3, strokeWidth }, 0);
  }

  private PathEffect getCornerDashPathEffect(int strokeWidth) {
    PathEffect dash = getDashPathEffect(strokeWidth);
    PathEffect corner = new CornerPathEffect(strokeWidth);
    return new ComposePathEffect(dash, corner);
  }

  private PathEffect getTrianglePathEffect(int strokeWidth) {
    return new PathDashPathEffect(
        getTriangle(strokeWidth),
        strokeWidth,
        0.0f,
        PathDashPathEffect.Style.ROTATE);
  }

  private Path getTriangle(float size) {
    Path path = new Path();
    float half = size / 2;
    path.moveTo(-half, -half);
    path.lineTo(half, -half);
    path.lineTo(0, half);
    path.close();
    return path;
  }

  private static class HollowSpan extends ReplacementSpan {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private int width;

    public HollowSpan(int strokeWidth) {
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(strokeWidth);
    }

    public void setPathEffect(PathEffect effect) {
      paint.setPathEffect(effect);
    }

    @Override
    public int getSize(
        Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
      this.paint.setColor(paint.getColor());

      width = (int) (paint.measureText(text, start, end) + this.paint.getStrokeWidth());
      return width;
    }

    @Override
    public void draw(
        Canvas canvas, CharSequence text, int start, int end,
        float x, int top, int y, int bottom, Paint paint) {
      path.reset();
      paint.getTextPath(text.toString(), start, end, x, y, path);
      path.close();

      canvas.translate(this.paint.getStrokeWidth() / 2, 0);
      canvas.drawPath(path, this.paint);
      canvas.translate(-this.paint.getStrokeWidth() / 2, 0);
    }
  }

  protected static class Option {
    public final String title;
    public final PathEffect effect;

    public Option(String title, PathEffect effect) {
      this.title = title;
      this.effect = effect;
    }

    @Override
    public String toString() {
      return title;
    }
  }
}