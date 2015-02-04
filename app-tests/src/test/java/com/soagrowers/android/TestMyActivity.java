package com.soagrowers.android;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMyActivity extends MyActivity {

  public TestMyActivity() {
    super();
    System.out.println("Constructed TestMyActivity.");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    System.out.println("Creating TestMyActivity.");
    super.onCreate(savedInstanceState);
  }

  @Override
  protected List<Object> getModules() {
    System.out.println("Getting Modules for TestMyActivity.");
    return Arrays.<Object>asList(new TestMyActivityModule());
  }
}
