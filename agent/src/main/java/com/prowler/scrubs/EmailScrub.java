package com.prowler.scrubs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailScrub implements Scrub {

  private static final String REDACTED_STRING = "***Redacted Email***";

  public static final Pattern EMAIL_ADDRESS
      = Pattern.compile(
      "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
          "\\@" +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
          "(" +
          "\\." +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
          ")+"
  );

  @Override
  public boolean containsSensitiveInformation(String text) {
    Matcher matcher = EMAIL_ADDRESS.matcher(text);
    return matcher.find();
  }

  @Override
  public String redact(String text) {
    Matcher matcher = EMAIL_ADDRESS.matcher(text);
    return matcher.replaceAll(REDACTED_STRING);
  }

  @Override
  public String violationType() {
    return "PERSONAL_INFORMATION";
  }
}
