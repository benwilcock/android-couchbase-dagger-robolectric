package com.soagrowers.android.dao;

import com.google.common.base.Optional;

import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.util.Map;

public class CouchbasePersistenceAdapter implements PersistenceAdapter {

  Database database;

  public CouchbasePersistenceAdapter(Database db) {
    this.database = db;
  }

  @Override
  public Map<String, Object> insert(Map<String, Object> map) throws PersistenceException {

    try {
      // Create a new document
      Document doc = database.createDocument();
      doc.putProperties(map);
      return doc.getProperties();
    } catch (Exception e) {
      throw new PersistenceException("Ouch. couldn't store that document.", e);
    }
  }

  @Override
  public Map<String, Object> get(String id) throws PersistenceException {
    // retrieve the document from the database
    Optional<Document> retrievedDocument = Optional.of(database.getExistingDocument(id));

    if (retrievedDocument.isPresent()) {
      return retrievedDocument.get().getProperties();
    } else {
      throw new PersistenceException("Couldn't find a Document with that ID: " + id);
    }
  }
}
