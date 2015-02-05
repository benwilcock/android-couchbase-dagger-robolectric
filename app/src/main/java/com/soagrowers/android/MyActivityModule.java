package com.soagrowers.android;

import android.app.Activity;
import android.app.Application;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.soagrowers.android.dao.CouchbasePersistenceAdapter;
import com.soagrowers.android.dao.PersistenceAdapter;
import com.soagrowers.android.dao.PersistenceException;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true,
        addsTo = BaseActivityModule.class,
        injects = MyActivity.class)
public class MyActivityModule {

  private static final String DATABASE_NAME = "my_couch_db";
  Activity mActivity;

  public MyActivityModule(Activity activity) {
    this.mActivity = activity;
  }

  /**
   * Needed by DaoModule.class to construct the Database
   */
  @Provides
  @Singleton
  Manager provideManger(Application application) {
    Manager manager;
    try {
      manager = new Manager(new AndroidContext(application), Manager.DEFAULT_OPTIONS);
      return manager;
    } catch (IOException ioe) {
      throw new PersistenceException("Unable to create a manager for injection");
    }
  }

  /**
   * Needed by CouchbasePersistenceAdapter.class Requires a Manager which is created above
   */
  @Provides
  @Singleton
  Database provideDatabase(Manager manager) {
    Database database;
    try {
      database = manager.getDatabase(DATABASE_NAME);
      return database;
    } catch (CouchbaseLiteException cble) {
      throw new PersistenceException("Unable to create a database for injection");
    }
  }

  /**
   * Needed by PersistenceManager.class (a concrete impl)
   */
  @Provides
  @Singleton
  PersistenceAdapter providePersistenceAdapter(Database db) {
    return new CouchbasePersistenceAdapter(db);
  }
}
