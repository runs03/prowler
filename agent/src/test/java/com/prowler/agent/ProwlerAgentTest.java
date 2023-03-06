package com.prowler.agent;

import java.util.List;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProwlerAgentTest {

  private final List<String> logLines = ImmutableList.of(
      "This line contains credit card 1234567890123456",
      "This line contains email runs@abc.com for user runs",
      "This line contains IP 105.1.1.0 for user runs"
  );

  @Test
  public void testProwlerAgent() {
    ProwlerContext context = new ProwlerContext("app1", "host1");
    ProwlerAgent agent = new ProwlerAgent(context);
    logLines.stream()
        .forEach(l -> agent.scan(l));
  }
}
