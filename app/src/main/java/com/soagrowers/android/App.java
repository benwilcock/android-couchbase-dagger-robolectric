package com.soagrowers.android;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;


public class App extends Application implements Injector {

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

  /**
   * Gets this Application's object graph.
   *
   * @return the object graph
   */
  @Override
  public ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }

  /**
   * Injects a target object using this Application's object graph.
   *
   * @param target the target object
   */
  public void inject(Object target) {
    checkState(mObjectGraph != null, "object graph must be initialized prior to calling inject");
    mObjectGraph.inject(target);
  }

  /**
   * Returns the list of dagger modules to be included in this Application's object graph.
   * Subclasses that override this method should add to the list returned by super.getModules().
   *
   * @return the list of modules
   */
  protected List<Object> getModules() {
    List<Object> result = new ArrayList<Object>();
    result.addAll(mSeedModules);
    result.add(new AppModule(this, this));
    return result;
  }

  public static void checkState(
      boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }
}
