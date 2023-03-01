package com.prowler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize()
public final class Violation {

  private final String violationId;
  private final String violationType;
  private final String hostName;
  private final String applicationName;
  private final String redactedLogLine;

  private Violation(String violationId, String violationType, String hostName,
      String applicationName,
      String redactedLogLine) {
    this.violationId = violationId;
    this.violationType = violationType;
    this.hostName = hostName;
    this.applicationName = applicationName;
    this.redactedLogLine = redactedLogLine;
  }

  @JsonProperty("violation_id")
  public String getViolationId() {
    return violationId;
  }

  @JsonProperty("violation_type")
  public String getViolationType() {
    return violationType;
  }

  @JsonProperty("hostname")
  public String getHostName() {
    return hostName;
  }

  @JsonProperty("application_name")
  public String getApplicationName() {
    return applicationName;
  }

  @JsonProperty("redacted_log_line")
  public String getRedactedLogLine() {
    return redactedLogLine;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private String violationId;
    private String violationType;
    private String hostName;
    private String applicationName;
    private String redactedLogLine;

    public Builder setViolationId(String violationId) {
      this.violationId = violationId;
      return this;
    }

    public Builder setViolationType(String violationType) {
      this.violationType = violationType;
      return this;
    }

    public Builder setHostName(String hostName) {
      this.hostName = hostName;
      return this;
    }

    public Builder setApplicationName(String applicationName) {
      this.applicationName = applicationName;
      return this;
    }

    public Builder setRedactedLogLine(String redactedLogLine) {
      this.redactedLogLine = redactedLogLine;
      return this;
    }

    public Violation build() {
      return new Violation(violationId, violationType, hostName, applicationName, redactedLogLine);
    }
  }

}
