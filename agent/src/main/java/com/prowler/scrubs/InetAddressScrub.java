package com.prowler.scrubs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InetAddressScrub implements Scrub {
  private static final String REDACTED_STRING = "***Redacted IP***";
  private static final Pattern PATTERN_IP_ADDRESS = Pattern.compile(
      "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
      + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
      + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
      + "|[1-9][0-9]|[0-9]))");

  @Override
  public String violationType() {
    return "PERSONAL_INFORMATION";
  }

  @Override
  public boolean containsSensitiveInformation(String text) {
    Matcher matcher = PATTERN_IP_ADDRESS.matcher(text);
    return matcher.find();
  }

  @Override
  public String redact(String text) {
    Matcher matcher = PATTERN_IP_ADDRESS.matcher(text);
    return matcher.replaceAll(REDACTED_STRING);
  }
}
