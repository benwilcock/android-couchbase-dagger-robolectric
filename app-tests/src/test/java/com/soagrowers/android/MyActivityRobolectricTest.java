package com.soagrowers.android;

import com.soagrowers.android.dao.PersistenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.ObjectGraph;

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
  Map<String, Object> result;

  @Inject
  PersistenceManager manager;

  @Before
  public void setup() {
    System.out.println("At setup field 'manager' is: " + manager);
    System.out.println("In setup activity 'manager' is: " + activity.manager);
    result = new HashMap<String, Object>();
    result.put("_id", "12345");
  }

  @Test
  public void testMyActivityAppearsAsExpectedInitially() {
    controller.create().start().resume();
    activity.inject(this);
    
    System.out.println("At test field 'manager' is: " + manager);
    System.out.println("In test activity 'manager' is: " + activity.manager);

    assertThat(activity.hello_text_view).hasText("Hello world!");
    assertThat(activity.document_id_text_view).hasText("Document id goes here after save");
    assertThat(activity.mClickMeBtn).hasText("Click Me");
    assertThat(activity.mSaveMeBtn).hasText("Save");
  }

  @Test
  public void testClickingClickMeButtonChangesHelloWorldText() {
    controller.create().start().resume();
    assertThat(activity.hello_text_view).hasText(R.string.hello_world);
    activity.mClickMeBtn.performClick();
    assertThat(activity.hello_text_view).hasText(R.string.ok_thanks);
  }

  @Test
  public void testClickingSaveButtonSavesMapAndDisplaysId() {
    controller.create().start().resume();
    assertThat(activity.document_id_text_view).hasText(R.string.document_id);
    when(activity.manager.insert(isA(HashMap.class))).thenReturn(result);
    activity.mSaveMeBtn.performClick();
    verify(activity.manager, times(1)).insert(any(HashMap.class));
    assertThat(activity.document_id_text_view).hasText("ID: 12345");
  }

}
