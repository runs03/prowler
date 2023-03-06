package com.prowler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

@JsonDeserialize(builder = FindViolationsResponse.Builder.class)
public final class FindViolationsResponse {

  private final List<Violation> violations;
  private final String nextPageToken;

  private FindViolationsResponse(List<Violation> violations, String nextPageToken) {
    this.violations = violations;
    this.nextPageToken = nextPageToken;
  }

  @JsonProperty("violations")
  public List<Violation> getViolations() {
    return violations;
  }

  @JsonProperty("nextPageToken")
  public String getNextPageToken() {
    return nextPageToken;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private List<Violation> violations;
    private String nextPageToken;

    public Builder setViolations(List<Violation> violations) {
      this.violations = violations;
      return this;
    }

    public Builder setNextPageToken(String nextPageToken) {
      this.nextPageToken = nextPageToken;
      return this;
    }

    public FindViolationsResponse build() {
      return new FindViolationsResponse(violations, nextPageToken);
    }
  }
}
