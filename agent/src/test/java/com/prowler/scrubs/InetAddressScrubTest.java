package com.prowler.scrubs;

import com.prowler.scrubs.InetAddressScrub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class InetAddressScrubTest {
  private final InetAddressScrub ipScrub = new InetAddressScrub();

  @Test
  public void testContainsSensitiveInformation() {
    String text = "This line contains IP 105.1.1.0 for user runs";
    Assert.assertTrue(ipScrub.containsSensitiveInformation(text));
  }

  @Test
  public void testContainsSensitiveInformation_noSensitiveInfo_returnsFalse() {
    String text = "This line contains IP 105.1.1 for user runs";
    Assert.assertFalse(ipScrub.containsSensitiveInformation(text));
  }

  @Test
  public void testRedact() {
    String text = "This line contains IP 105.1.1.0 for user runs";
    Assert.assertEquals(ipScrub.redact(text), "This line contains IP ***Redacted IP*** for user runs");
  }
}
