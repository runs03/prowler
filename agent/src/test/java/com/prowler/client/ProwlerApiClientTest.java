package com.prowler.client;

import com.prowler.models.Violation;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProwlerApiClientTest {

  @Test
  public void testViolations() {
    ProwlerApiClient client = new ProwlerApiClient();
    Violation violation = Violation.newBuilder()
        .setViolationType("FINANCIAL")
        .setRedactedLogLine("*****Log Line*****")
        .setViolationTimestamp(LocalDateTime.now())
        .setApplicationName("app1")
        .setHostname("host.name.goog")
        .build();
    Violation createdViolation = client.reportViolation(violation);
    System.out.println("Created Violation : " + createdViolation);
  }
}
