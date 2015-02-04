package com.soagrowers.android;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;


public class App extends Application {

  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    objectGraph = ObjectGraph.create(getModules().toArray());
    objectGraph.inject(this);
  }

  protected ObjectGraph getObjectGraph() {
    return objectGraph;
  }

  public List<Object> getModules() {
    return Arrays.<Object>asList(new AppModule(this));
  }

  public ObjectGraph createScopedGraph(Object... modules) {
    return objectGraph.plus(modules);
  }
}
