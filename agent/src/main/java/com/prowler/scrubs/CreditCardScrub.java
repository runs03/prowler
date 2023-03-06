package com.prowler.scrubs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CreditCardScrub implements Scrub {

  private static final String REDACTED_STRING = "***Redacted Card***";
  private static final Pattern CARD_PATTERN = Pattern.compile("\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b");

  @Override
  public boolean containsSensitiveInformation(String text) {
    Matcher matcher = CARD_PATTERN.matcher(text);
    return matcher.find();
  }

  @Override
  public String redact(String text) {
    Matcher matcher = CARD_PATTERN.matcher(text);
    return matcher.replaceAll(REDACTED_STRING);
  }

  @Override
  public String violationType() {
    return "FINANCIAL";
  }
}
