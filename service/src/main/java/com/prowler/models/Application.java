package com.prowler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Application.Builder.class)
public final class Application {
  private final String name;
  private final String owner;

  private final String description;

  private Application(String name, String owner, String description) {
    this.name = name;
    this.owner = owner;
    this.description = description;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("owner")
  public String getOwner() {
    return owner;
  }

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "Application{" +
        "name='" + name + '\'' +
        ", owner='" + owner + '\'' +
        ", description='" + description + '\'' +
        '}';
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private String name;
    private String owner;
    private String description;

    public Builder setName(String value) {
      this.name = value;
      return this;
    }

    public Builder setOwner(String owner) {
      this.owner = owner;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Application build() {
      return new Application(name, owner, description);
    }
  }
}
