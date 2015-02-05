package com.soagrowers.android;

import com.soagrowers.android.dao.PersistenceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class MyActivityRobolectricTest {

  ActivityController<TestMyActivity> controller = Robolectric.buildActivity(TestMyActivity.class);
  MyActivity activity = controller.get();

  @Inject
  PersistenceManager manager;

  @Test
  public void testMyActivityAppearsAsExpectedInitially() {
    controller.create().start().resume();
    assertThat(activity.document_id_text_view).hasText("Document id goes here after save");
    assertThat(activity.mSaveMeBtn).hasText("Save");
  }


  @Test
  public void testClickingSaveButtonSavesMapAndDisplaysId() {
    controller.create().start().resume();

    // This is important if we want the local 'manager' field to be injected!
    activity.inject(this);

    Map<String, Object> result = new HashMap<String, Object>();
    result.put("_id", "12345");

    assertThat(activity.document_id_text_view).hasText(R.string.document_id);
    when(manager.insert(isA(HashMap.class))).thenReturn(result);
    activity.mSaveMeBtn.performClick();
    verify(manager, times(1)).insert(any(HashMap.class));
    assertThat(activity.document_id_text_view).hasText("ID: 12345");
  }

}
