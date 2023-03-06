package com.prowler.agent;

import com.prowler.client.ProwlerApiClient;
import com.prowler.models.Violation;
import java.time.LocalDateTime;

class DefaultReporter implements Reporter {
  private final ProwlerContext context;
  private final ProwlerApiClient apiClient;

  public DefaultReporter(ProwlerContext context, ProwlerApiClient apiClient) {
    this.context = context;
    this.apiClient = apiClient;
  }

  @Override
  public void reportLogLine(String redactedLogLine, String violationType) {
    Violation reportedViolation = apiClient.reportViolation(
        Violation.newBuilder()
            .setViolationType(violationType)
            .setHostname(context.getHostname())
            .setApplicationName(context.getApplicationName())
            .setRedactedLogLine(redactedLogLine)
            .setViolationTimestamp(LocalDateTime.now()) // This should be parsed from log line
            .build());
    System.out.println("Reported violation to prowler service violation = " + reportedViolation);
  }
}
