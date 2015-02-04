package com.soagrowers.android.dao;

import java.util.Map;

import javax.inject.Inject;

public class PersistenceManager {

  @Inject
  PersistenceAdapter persistenceAdapter;

  public PersistenceManager() {
  }

  public Map insert(Map map) {

    map = persistenceAdapter.insert(map);
    return map;
  }

  public Map<String, Object> get(String id) {
    Map map = persistenceAdapter.get(id);
    return map;
  }
}
