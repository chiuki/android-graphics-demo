package com.sqisland.android.graphics_demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

public abstract class SpinnerImageActivity extends Activity {
  protected ImageView imageView;
  private int lastSelection = 0;
  private Spinner spinner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spinner_image);

    imageView = (ImageView) findViewById(R.id.image);
    Bitmap original = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

    final List<Option> options = getOptions(original);
    spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<Option> adapter = new ArrayAdapter<Option>(
        this, android.R.layout.simple_spinner_item, options);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Option option = options.get(position);
        imageView.setImageBitmap(option.bitmap);
        if (position != 0) {
          lastSelection = position;
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spinner.setSelection(getInitialSelection(options.size()));
    lastSelection = spinner.getSelectedItemPosition();

    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (spinner.getSelectedItemPosition() == 0) {
          spinner.setSelection(lastSelection);
        } else {
          spinner.setSelection(0);
        }
      }
    });
  }

  protected abstract List<Option> getOptions(Bitmap original);
  protected int getInitialSelection(int size) {
    return 1;
  }

  protected static class Option {
    public final String title;
    public final Bitmap bitmap;

    public Option(String title, Bitmap bitmap) {
      this.title = title;
      this.bitmap = bitmap;
    }

    @Override
    public String toString() {
      return title;
    }
  }
}