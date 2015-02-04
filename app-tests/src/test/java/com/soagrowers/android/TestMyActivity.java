package com.soagrowers.android;

import java.util.ArrayList;
import java.util.List;

public class TestMyActivity extends MyActivity {

  public TestMyActivity() {
    super();
    System.out.println("Constructed TestMyActivity.");
  }

  @Override
  protected List<Object> getModules() {
    List<Object> result = new ArrayList<>();
    result.add(new TestMyActivityModule());
    return result;
  }
}
