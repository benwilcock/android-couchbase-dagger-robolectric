package com.soagrowers.android;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import static org.assertj.android.api.Assertions.assertThat;

public class MyActivityInstrumentation extends ActivityInstrumentationTestCase2<MyActivity> {

  private MyActivity mActivity;

  public MyActivityInstrumentation() {
    super(MyActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    mActivity = getActivity();
  }

  @UiThreadTest
  public void testDaggerHasInjectedThePersistenceManager(){
    assertNotNull(mActivity.manager);
  }


  @UiThreadTest
  public void testMyActivityAppearsAsExpectedInitially() {
    assertThat(mActivity.hello_text_view).isVisible();
    assertThat(mActivity.hello_text_view).hasText("Hello world!");
    assertThat(mActivity.document_id_text_view).hasText("Document id goes here after save");
    assertThat(mActivity.mClickMeBtn).hasText("Click Me");
    assertThat(mActivity.mSaveMeBtn).hasText("Save");
  }

  @UiThreadTest
  public void testClickingClickMeButtonChangesHelloWorldText() {
    assertThat(mActivity.hello_text_view).hasText(R.string.hello_world);
    mActivity.mClickMeBtn.performClick();
    assertThat(mActivity.hello_text_view).hasText(R.string.ok_thanks);
  }

  @UiThreadTest
  public void testClickingSaveButtonChangesText() {
    assertThat(mActivity.document_id_text_view).hasText(R.string.document_id);
    mActivity.mSaveMeBtn.performClick();
    assertThat(mActivity.document_id_text_view).doesNotContainText("Document");
    assertThat(mActivity.document_id_text_view).doesNotContainText("null");
    assertThat(mActivity.document_id_text_view).containsText("ID: ");
  }
}
