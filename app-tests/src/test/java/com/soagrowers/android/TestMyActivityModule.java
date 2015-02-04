package com.soagrowers.android;

import com.soagrowers.android.dao.PersistenceManager;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {TestMyActivity.class, MyActivityRobolectricTest.class},
    overrides = true,
    complete = true
)
public class TestMyActivityModule {

  private PersistenceManager mockManager;

  public TestMyActivityModule() {
  }

  @Provides
  @Singleton
  public PersistenceManager providePersistenceManager() {
    mockManager = Mockito.mock(PersistenceManager.class);
    return mockManager;
  }
}
