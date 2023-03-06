package com.prowler.scrubs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CreditCardScrubTest {

  private final CreditCardScrub creditCardScrub = new CreditCardScrub();

  @Test
  public void testContainsSensitiveInformation() {
    String text = "This line contains credit card 1234567890123456";
    Assert.assertTrue(creditCardScrub.containsSensitiveInformation(text));
  }

  @Test
  public void testRedact() {
    String text = "This line contains credit card 1234567890123456";
    Assert.assertEquals(
        creditCardScrub.redact(text),
        "This line contains credit card ***Redacted Card***");
  }
}
