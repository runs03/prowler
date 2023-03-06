package com.prowler.agent;

public final class ProwlerContext {
  private final String applicationName;
  private final String hostname;

  public ProwlerContext(String applicationName, String hostname) {
    this.applicationName = applicationName;
    this.hostname = hostname;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public String getHostname() {
    return hostname;
  }
}
