package com.soagrowers.android;

import dagger.Module;

@Module(injects = {App.class})
public class AppModule {

  private App app;

  public AppModule(App app) {
    this.app = app;
  }
}
