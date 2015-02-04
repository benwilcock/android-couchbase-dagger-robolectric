package com.soagrowers.android;

import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.util.Log;
import android.widget.TextView;

import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Created by Ben on 20/01/2015.
 */
public class MyActivityRobotium extends ActivityInstrumentationTestCase2<MyActivity> {

  Solo solo;

  public MyActivityRobotium() {
    super(MyActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    solo = new Solo(getInstrumentation(), getActivity());
  }

  @Override
  public void tearDown() throws Exception {
    solo.finishOpenedActivities();
  }

  public void testMyActivityWithRobotium() throws InterruptedException {
    // check that we have the right activity
    solo.assertCurrentActivity("wrong activity", MyActivity.class);
    Assert.assertTrue(solo.searchText(getActivity().getString(R.string.hello_world)));

    // Test the 'Click Me' button
    Assert.assertTrue(solo.waitForText(getActivity().getString(R.string.hello_world)));
    solo.clickOnButton(solo.getString(R.string.click_me));
    Assert.assertTrue(solo.waitForText(getActivity().getString(R.string.ok_thanks)));

    //Test the 'Save' button, stores something.
    Assert.assertTrue(solo.waitForText(getActivity().getString(R.string.document_id)));
    solo.clickOnButton(solo.getString(R.string.save));
    Assert.assertTrue(solo.searchText("ID:"));
    Assert.assertFalse(solo.searchText("null"));
  }
}
