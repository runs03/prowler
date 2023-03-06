package com.prowler.scrubs;

/**
 * Defines the logic for detecting and redacting sensitive information from log lines.
 */
public interface Scrub {
  boolean containsSensitiveInformation(String text);

  String redact(String text);

  String violationType();
}
