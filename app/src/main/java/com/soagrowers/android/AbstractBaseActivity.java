package com.soagrowers.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public abstract class AbstractBaseActivity extends ActionBarActivity {

  private ObjectGraph og;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    App application = (App) getApplication();
    List<Object> modules = new ArrayList<Object>();
    modules.addAll(application.getModules());
    modules.addAll(getModules());
    
    og = ObjectGraph.create(modules.toArray());
    og.inject(application);
    og.inject(this);
    
    super.onCreate(savedInstanceState);
  }
  
  protected void inject(Object object){
    if(null != og && null != object){
      og.inject(object);
    }
    
  }

  protected abstract List<Object> getModules();

  @Override
  protected void onDestroy() {
    super.onDestroy();
    og = null;
  }
}
