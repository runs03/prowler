package com.prowler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prowler.serdes.LocalDateTimeDeserializer;
import com.prowler.serdes.LocalDateTimeSerializer;
import java.time.LocalDateTime;

@JsonDeserialize(builder = Violation.Builder.class)
public final class Violation {

  @JsonProperty("violationId")
  private final String violationId;

  @JsonProperty("violationType")
  private final String violationType;

  @JsonProperty("hostname")
  private final String hostName;

  @JsonProperty("applicationName")
  private final String applicationName;

  @JsonProperty("redactedLogLine")
  private final String redactedLogLine;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonProperty("violationTimestamp")
  private final LocalDateTime violationTimestamp;

  private Violation(String violationId, String violationType, String hostName,
      String applicationName,
      String redactedLogLine,
      LocalDateTime violationTimestamp) {
    this.violationId = violationId;
    this.violationType = violationType;
    this.hostName = hostName;
    this.applicationName = applicationName;
    this.redactedLogLine = redactedLogLine;
    this.violationTimestamp = violationTimestamp;
  }

  public String getViolationId() {
    return violationId;
  }

  public String getViolationType() {
    return violationType;
  }

  public String getHostName() {
    return hostName;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public String getRedactedLogLine() {
    return redactedLogLine;
  }

  public LocalDateTime getViolationTimestamp() {
    return violationTimestamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "Violation{" +
        "violationId='" + violationId + '\'' +
        ", violationType='" + violationType + '\'' +
        ", hostName='" + hostName + '\'' +
        ", applicationName='" + applicationName + '\'' +
        ", redactedLogLine='" + redactedLogLine + '\'' +
        ", violationTimestamp=" + violationTimestamp +
        '}';
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private String violationId;
    private String violationType;
    private String hostname;
    private String applicationName;
    private String redactedLogLine;
    private LocalDateTime violationTimestamp;

    public Builder setViolationId(String violationId) {
      this.violationId = violationId;
      return this;
    }

    public Builder setViolationType(String violationType) {
      this.violationType = violationType;
      return this;
    }

    public Builder setHostname(String hostname) {
      this.hostname = hostname;
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public Builder setViolationTimestamp(LocalDateTime violationTimestamp) {
      this.violationTimestamp = violationTimestamp;
      return this;
    }


    public Violation build() {
      return new Violation(
          violationId,
          violationType,
          hostname,
          applicationName,
          redactedLogLine,
          violationTimestamp);
    }
  }

}
