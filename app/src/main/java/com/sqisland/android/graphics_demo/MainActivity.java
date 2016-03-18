package com.sqisland.android.graphics_demo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView footer = (TextView) LayoutInflater.from(this).inflate(
        android.R.layout.simple_list_item_1, getListView(), false);
    footer.setText(R.string.about);
    getListView().addFooterView(footer);

    final ArrayList<Demo> demos = new ArrayList<Demo>();
    demos.add(new Demo(this, RainbowGradientActivity.class, R.string.rainbow_gradient));
    demos.add(new Demo(this, PatternedTextActivity.class, R.string.patterned_text));
    demos.add(new Demo(this, PeekThroughActivity.class, R.string.peek_through));
    demos.add(new Demo(this, ClipRectActivity.class, R.string.clip_rect));
    demos.add(new Demo(this, ColorMatrixActivity.class, R.string.color_matrix));
    demos.add(new Demo(this, FourColorsActivity.class, R.string.four_colors));
    demos.add(new Demo(this, PorterDuffActivity.class, R.string.porter_duff));
    demos.add(new Demo(this, HollowTextActivity.class, R.string.hollow_text));
    demos.add(new Demo(this, EmbossMaskFilterActivity.class, R.string.emboss_mask_filter));
    demos.add(new Demo(this, BlurMaskFilterActivity.class, R.string.blur_mask_filter));

    if (Build.VERSION.SDK_INT >= 17) {
      demos.add(new Demo(this,
          RenderscriptBlurActivity.class, R.string.renderscript_blur));
      demos.add(new Demo(this,
          RenderscriptConvolutionActivity.class, R.string.renderscript_convolution));
    }

    ArrayAdapter<Demo> adapter = new ArrayAdapter<Demo>(
        this,
        android.R.layout.simple_list_item_1,
        demos);
    getListView().setAdapter(adapter);

    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position < demos.size()) {
          Demo demo = demos.get(position);
          startActivity(new Intent(MainActivity.this, demo.activityClass));
        } else {
          startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
      }
    });
  }

  public static class Demo {
    public final Class<?> activityClass;
    public final String title;

    public Demo(Context context, Class<?> activityClass, int titleId) {
      this.activityClass = activityClass;
      this.title = context.getString(titleId);
    }

    @Override
    public String toString() {
      return this.title;
    }
  }
}
