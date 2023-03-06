package com.prowler.scrubs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class EmailScrubTest {

  private final EmailScrub emailScrub = new EmailScrub();

  @Test
  public void testContainsSensitiveInformation() {
    String text = "This line contains email runs@abc.com for user runs";
    Assert.assertTrue(emailScrub.containsSensitiveInformation(text));
  }

  @Test
  public void testRedact() {
    String text = "This line contains email runs@abc.com for user runs";
    Assert.assertEquals(emailScrub.redact(text), "This line contains email ***Redacted Email*** for user runs");
  }
}
