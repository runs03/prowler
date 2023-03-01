package com.prowler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Application.Builder.class)
public final class Application {
  private final String name;
  private final String owner;

  private Application(String name, String owner) {
    this.name = name;
    this.owner = owner;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("owner")
  public String getOwner() {
    return owner;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private String name;
    private String owner;

    public Builder setName(String value) {
      this.name = value;
      return this;
    }

    public Builder setOwner(String owner) {
      this.owner = owner;
      return this;
    }

    public Application build() {
      return new Application(name, owner);
    }
  }
}
