package com.soagrowers.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soagrowers.android.dao.PersistenceManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class MyActivity extends AbstractBaseActivity {

  TextView hello_text_view;
  TextView document_id_text_view;
  Button mClickMeBtn;
  Button mSaveMeBtn;

  @Inject
  PersistenceManager manager;


  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(new MyActivityModule(this));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);
    hello_text_view = (TextView) findViewById(R.id.hello_text_view);
    mClickMeBtn = (Button) findViewById(R.id.click_me_button);
    document_id_text_view = (TextView) findViewById(R.id.document_id_text_view);
    mSaveMeBtn = (Button) findViewById(R.id.save_button);
  }


  public void clickMeBtnPressed(View view) {
    hello_text_view.setText(getString(R.string.ok_thanks));
  }

  public void saveBtnPressed(View view) {
    Map<String, Object> document = new HashMap<String, Object>();
    document.put("MyBlog", "http://benwilcock.wordpress.com");
    document = manager.insert(document);
    document_id_text_view.setText("ID: " + document.get("_id"));
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.my, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
