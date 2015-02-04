package com.soagrowers.android.dao;

import java.util.Map;

public interface PersistenceAdapter {

  public Map<String, Object> insert(Map<String, Object> map) throws PersistenceException;

  public Map<String, Object> get(String id) throws PersistenceException;
}
