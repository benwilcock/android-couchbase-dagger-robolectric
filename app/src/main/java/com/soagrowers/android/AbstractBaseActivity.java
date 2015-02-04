package com.soagrowers.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

import dagger.ObjectGraph;

public abstract class AbstractBaseActivity extends ActionBarActivity {

  private ObjectGraph activityGraph;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
    activityGraph.inject(this);
  }

  protected abstract List<Object> getModules();

  @Override
  protected void onDestroy() {
    super.onDestroy();
    activityGraph = null;
  }
}
