package com.soagrowers.android.dao;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersistenceManagerTest {

  ObjectGraph graph;

  @Inject
  PersistenceManager manager;

  @Inject
  PersistenceAdapter persistenceAdapter;

  Map<String, Object> map;
  String id;

  @Before
  public void setUp() {

    graph = ObjectGraph.create(new TestModule());
    graph.inject(this);

    map = new HashMap<String, Object>();
    map.put("MyBlog", "http://benwilcock.wordpress.com");
  }

  @Module(
      injects = PersistenceManagerTest.class,
      overrides = true,
      complete = true
  )
  static class TestModule {

    @Provides
    @Singleton
    PersistenceAdapter provideDatabase() {
      return Mockito.mock(PersistenceAdapter.class);
    }
  }

  @Test
  public void testInsert() {
    assertNotNull(persistenceAdapter);
    when(persistenceAdapter.insert(map)).thenReturn(map);
    Map<String, Object> result = manager.insert(map);
    verify(persistenceAdapter, times(1)).insert(map);
  }

  @Test
  public void testGet() {
    assertNotNull(persistenceAdapter);
    when(persistenceAdapter.get(id)).thenReturn(map);
    Map<String, Object> map = manager.get(id);
    verify(persistenceAdapter, times(1)).get(id);
  }
}
