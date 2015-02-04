package com.soagrowers.android;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;


public class App extends Application {


  public List<Object> getModules() {
    return Arrays.<Object>asList(new AppModule(this));
  }

}
