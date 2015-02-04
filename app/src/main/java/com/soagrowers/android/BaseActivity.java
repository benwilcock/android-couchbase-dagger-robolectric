package com.soagrowers.android;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public abstract class BaseActivity extends ActionBarActivity implements Injector {

  ObjectGraph mObjectGraph;

  @Override
  public final ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }

  @Override
  public void inject(Object target) {
    App.checkState(mObjectGraph != null, "object graph must be assigned prior to calling inject");
    mObjectGraph.inject(target);
  }

  @Override
  protected void onCreate(android.os.Bundle savedInstanceState) {
    // expand the application graph with the mActivity-specific module(s)
    ObjectGraph appGraph = ((Injector) getApplication()).getObjectGraph();
    List<Object> activityModules = getModules();
    mObjectGraph = appGraph.plus(activityModules.toArray());

    // now we can inject ourselves
    inject(this);

    // note: we do the graph setup and injection before calling super.onCreate so that InjectingFragments
    // associated with this InjectingActivity can do their graph setup and injection in their
    // onAttach override.
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    // Eagerly clear the reference to the mActivity graph to allow it to be garbage collected as
    // soon as possible.
    mObjectGraph = null;
    super.onDestroy();
  }

  /**
   * Returns the list of dagger modules to be included in this ActionBarActivity's object graph.
   * Subclasses that override this method should add to the list returned by super.getModules().
   *
   * @return the list of modules
   */
  protected List<Object> getModules() {
    List<Object> result = new ArrayList<Object>();
    result.add(new BaseActivityModule(this, this));
    return result;
  }
}
