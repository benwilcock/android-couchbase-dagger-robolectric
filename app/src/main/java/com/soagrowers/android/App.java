package com.soagrowers.android;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;


public class App extends Application {

  private List<Object> mSeedModules = new ArrayList<Object>();
  private ObjectGraph mObjectGraph;

  public void addSeedModules(List<Object> modules) {
    mSeedModules.addAll(modules);
  }

  /**
   * Creates an object graph for this Application using the modules returned by {@link
   * #getModules()}. <p/> Injects this Application using the created graph.
   */
  @Override
  public void onCreate() {
    super.onCreate();

    // initialize object graph and inject this
    mObjectGraph = ObjectGraph.create(getModules().toArray());
    mObjectGraph.inject(this);

    // debug mode stuff
    if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) == 1) {
      mObjectGraph.validate(); // validate dagger's object graph
    }
  }


  public ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }


  public void inject(Object target) {
    checkState(mObjectGraph != null, "object graph must be initialized prior to calling inject");
    mObjectGraph.inject(target);
  }


  protected List<Object> getModules() {
    List<Object> result = new ArrayList<Object>();
    result.addAll(mSeedModules);
    result.add(new AppModule(this));
    return result;
  }

  public static void checkState(
      boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }
}
