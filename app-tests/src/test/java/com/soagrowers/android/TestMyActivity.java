package com.soagrowers.android;

import com.soagrowers.android.dao.PersistenceManager;

import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

public class TestMyActivity extends MyActivity {

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(new TestMyActivityModule());
  }

  @Module(
      addsTo = BaseActivityModule.class,
      injects = {TestMyActivity.class, MyActivityRobolectricTest.class},
      overrides = true,
      library = true
  )
  class TestMyActivityModule {
    @Provides
    @Singleton
    public PersistenceManager providePersistenceManager() {
      return Mockito.mock(PersistenceManager.class);
    }
  }
}
