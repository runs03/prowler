package com.prowler.datastore;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.spanner.Database;
import com.google.cloud.spanner.DatabaseAdminClient;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spanner.Struct;
import com.google.common.collect.ImmutableList;
import com.google.spanner.admin.database.v1.CreateDatabaseMetadata;
import com.prowler.models.Application;
import com.prowler.models.Violation;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProwlerSpannerStore {
  private static final String VIOLATIONS_TABLE_NAME = "Violations";
  private static final String APPLICATIONS_TABLE_NAME = "Applications";
  private static final String EMULATOR_HOST = System.getenv("SPANNER_EMULATOR_HOST");
  private static final String INSTANCE_ID = "prowler-instance";
  private static final String DATABASE_ID = "prowler-database";
  private static final String PROJECT_ID = "prowler-spanner";

  private enum ApplicationsColumns {
    APPLICATION_NAME("ApplicationName"),
    OWNER("Owner"),
    DESCRIPTION("Description");

    private final String val;

    ApplicationsColumns(String val) {
      this.val = val;
    }

    public String getValue() {
      return val;
    }
  }

  private enum ViolationsColumns {
    APPLICATION_NAME("ApplicationName"),
    VIOLATION_TIMESTAMP("ViolationTimestamp"),
    VIOLATION_ID("ViolationId"),
    HOSTNAME("Hostname"),
    VIOLATION_TYPE("ViolationType"),
    REDACTED_LOG_LINE("RedactedLogLine");

    private final String val;

    ViolationsColumns(String val) {
      this.val = val;
    }

    public String getValue() {
      return val;
    }
  }

  private static final Application APP1 = Application.newBuilder()
      .setDescription("Test application 1")
      .setOwner("runs@google.com")
      .setName("App-1")
      .build();
  private static final Application APP2 = Application.newBuilder()
      .setDescription("Test application 2")
      .setOwner("no-runs@google.com")
      .setName("App-2")
      .build();
  private static final String VIOLATIONS_TABLE_DDL = "CREATE TABLE Violations ("
      + "  ApplicationName STRING(256) NOT NULL,"
      + "  ViolationTimestamp TIMESTAMP NOT NULL,"
      + "  ViolationId STRING(256) NOT NULL,"
      + "  Hostname STRING(256) NOT NULL,"
      + "  ViolationType STRING(64),"
      + "  RedactedLogLine STRING(MAX)"
      + ") PRIMARY KEY (ApplicationName, ViolationTimestamp DESC, ViolationId);";
  private static final String APPLICATIONS_TABLE_DDL = "CREATE TABLE Applications ("
      + "  ApplicationName  STRING(256) NOT NULL,"
      + "  Owner STRING(256) NOT NULL,"
      + "  Description STRING(MAX)"
      + ") PRIMARY KEY (ApplicationName);";
  private static DatabaseClient dbClient;
  private static DatabaseAdminClient dbAdminClient;
  private static DatabaseId prowlerDbId;

  public static void setup() {
    initializeSpanner();
    createDatabase();
    createApplication(APP1);
    createApplication(APP2);
  }

  private static void initializeSpanner() {
    SpannerOptions options = SpannerOptions.newBuilder()
        .setEmulatorHost(EMULATOR_HOST)
        .setProjectId(PROJECT_ID)
        .build();
    Spanner spanner = options.getService();
    prowlerDbId = DatabaseId.of(PROJECT_ID, INSTANCE_ID, DATABASE_ID);
    dbClient = spanner.getDatabaseClient(prowlerDbId);
    dbAdminClient = spanner.getDatabaseAdminClient();
  }

  private static void createDatabase() {
    OperationFuture<Database, CreateDatabaseMetadata> opFuture =  dbAdminClient.createDatabase(
        prowlerDbId.getInstanceId().getInstance(),
        prowlerDbId.getDatabase(),
        Arrays.asList(APPLICATIONS_TABLE_DDL, VIOLATIONS_TABLE_DDL));
    try {
      Database db = opFuture.get();
      System.out.println("Created database [" + db.getId() + "]");
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  public static void createApplication(Application application) {
    Mutation mutation = Mutation.newInsertBuilder(APPLICATIONS_TABLE_NAME)
        .set("ApplicationName")
        .to(application.getName())
        .set("Owner")
        .to(application.getOwner())
        .set("Description")
        .to(application.getDescription())
        .build();
    dbClient.write(ImmutableList.of(mutation));
  }

  public static Application getApplication(String applicationName) {
    try (ResultSet resultSet =
        dbClient
            .singleUse() // Execute a single read or query against Cloud Spanner.
            .executeQuery(Statement.of("SELECT ApplicationName, Owner, Description FROM Applications"))) {
      while (resultSet.next()) {
        Struct record = resultSet.getCurrentRowAsStruct();
        Application application =  Application.newBuilder()
            .setName(record.getString(ApplicationsColumns.APPLICATION_NAME.getValue()))
            .setOwner(record.getString(ApplicationsColumns.OWNER.getValue()))
            .setDescription(record.getString(ApplicationsColumns.DESCRIPTION.getValue()))
            .build();
        System.out.printf("Found application : " + application);
        return application;
      }
    }
    return null;
  }

  public static void reportViolation(Violation violation) {
    Mutation mutation = Mutation.newInsertBuilder(VIOLATIONS_TABLE_NAME)
        .set(ViolationsColumns.APPLICATION_NAME.getValue())
        .to(violation.getApplicationName())
        .set(ViolationsColumns.VIOLATION_TIMESTAMP.getValue())
        .to(Timestamp.MAX_VALUE)
        .set(ViolationsColumns.VIOLATION_ID.getValue())
        .to(violation.getViolationId())
        .set(ViolationsColumns.HOSTNAME.getValue())
        .to(violation.getHostName())
        .set(ViolationsColumns.VIOLATION_TYPE.getValue())
        .to(violation.getViolationType())
        .set(ViolationsColumns.REDACTED_LOG_LINE.getValue())
        .to(violation.getRedactedLogLine())
        .build();
    dbClient.write(ImmutableList.of(mutation));
  }

  public static List<Violation> findViolations() {
    return null;
  }
}
