package com.soagrowers.android;

import java.util.Arrays;
import java.util.List;

public class TestMyActivity extends MyActivity {

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(new TestMyActivityModule());
  }
}
