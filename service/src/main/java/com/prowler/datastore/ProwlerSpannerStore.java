package com.prowler.datastore;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;

public final class ProwlerSpannerStore {

  private DatabaseClient dbClient;
  private static final String instanceId = "prowler-instance";
  private static final String databaseId = "prowler-database";

  public void initialize() {
    SpannerOptions options = SpannerOptions.newBuilder().build();
    Spanner spanner = options.getService();
    this.dbClient =  spanner.getDatabaseClient(DatabaseId.of(options.getProjectId(), instanceId, databaseId));
  }

  public void read() {
    ResultSet resultSet = dbClient.singleUse().executeQuery(Statement.of("select * from Singers"));
    System.out.println("\n\nResults:");
    // Prints the results
    while (resultSet.next()) {
      System.out.println("result line = " + resultSet.getCurrentRowAsStruct());
    }
  }
}
