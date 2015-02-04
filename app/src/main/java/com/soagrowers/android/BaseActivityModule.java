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
  private Injector mInjector;

  /**
   * Class constructor.
   *
   * @param activity the Activity with which this module is associated.
   * @param injector the Injector for the Application-scope graph
   */
  public BaseActivityModule(android.app.Activity activity, Injector injector) {
    mActivity = activity;
    mInjector = injector;
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

  /**
   * Provides the Activity
   *
   * @return the Activity
   */
  @Provides
  public android.app.Activity provideActivity() {
    return mActivity;
  }

  /**
   * Provides the Injector for the Activity-scope graph
   *
   * @return the Injector
   */
  @Provides
  @Activity
  public Injector provideActivityInjector() {
    return mInjector;
  }

  /**
   * Defines an qualifier annotation which can be used in conjunction with a type to identify
   * dependencies within the object graph.
   *
   * @see <a href="http://square.github.io/dagger/">the dagger documentation</a>
   */
  @Qualifier
  @Target({FIELD, PARAMETER, METHOD})
  @Documented
  @Retention(RUNTIME)
  public @interface Activity {

  }

}
