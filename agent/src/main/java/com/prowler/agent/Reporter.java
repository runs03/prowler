package com.prowler.agent;

public interface Reporter {
  void reportLogLine(String redactedLogLine, String violationType);
}
