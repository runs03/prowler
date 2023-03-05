package com.prowler.models;

import java.time.LocalDateTime;

public final class FindViolationsRequest {

  private final String applicationName;

  private final LocalDateTime start;
  private final LocalDateTime end;
  private final int pageSize;
  private final String pageToken;

  private FindViolationsRequest(String applicationName, LocalDateTime start, LocalDateTime end, int pageSize,
      String pageToken) {
    this.applicationName = applicationName;
    this.start = start;
    this.end = end;
    this.pageSize = pageSize;
    this.pageToken = pageToken;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public int getPageSize() {
    return pageSize;
  }

  public String getPageToken() {
    return pageToken;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private LocalDateTime start;
    private LocalDateTime end;
    private int pageSize;
    private String pageToken;
    private String applicationName;

    public Builder setStart(LocalDateTime start) {
      this.start = start;
      return this;
    }

    public Builder setEnd(LocalDateTime end) {
      this.end = end;
      return this;
    }

    public Builder setPageSize(int pageSize) {
      this.pageSize = pageSize;
      return this;
    }

    public Builder setPageToken(String pageToken) {
      this.pageToken = pageToken;
      return this;
    }

    public Builder setApplicationName(String applicationName) {
      this.applicationName = applicationName;
      return this;
    }

    public FindViolationsRequest build() {
      return new FindViolationsRequest(applicationName, start, end, pageSize, pageToken);
    }
  }
}
