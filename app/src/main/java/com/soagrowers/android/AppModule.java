package com.soagrowers.android;

import android.content.Context;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module(library = true,
        injects = App.class)
public class AppModule {

  private App mApp;

  public AppModule(App app) {
    this.mApp = app;
  }

  @Provides
  @Singleton
  @Application
  public Context provideApplicationContext() {
    return mApp.getApplicationContext();
  }

  @Provides
  @Singleton
  public android.app.Application provideApplication() {
    return mApp;
  }

  @Qualifier
  @Target({FIELD, PARAMETER, METHOD})
  @Documented
  @Retention(RUNTIME)
  public @interface Application {

  }
}
