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

@Module(library = true, addsTo = AppModule.class)
public class BaseActivityModule {

  private android.app.Activity mActivity;


  public BaseActivityModule(android.app.Activity activity) {
    mActivity = activity;
  }

  /**
   * Provides the Activity Context
   *
   * @return the Activity Context
   */
  @Provides
  @Singleton
  @Activity
  public android.content.Context provideActivityContext() {
    return (Context) mActivity;
  }

  @Provides
  public android.app.Activity provideActivity() {
    return mActivity;
  }

  @Qualifier
  @Target({FIELD, PARAMETER, METHOD})
  @Documented
  @Retention(RUNTIME)
  public @interface Activity {

  }

}
