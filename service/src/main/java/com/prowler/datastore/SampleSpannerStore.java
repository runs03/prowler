package com.prowler.datastore;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.spanner.Database;
import com.google.cloud.spanner.DatabaseAdminClient;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;
import com.google.spanner.admin.database.v1.CreateDatabaseMetadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class SampleSpannerStore {

  private DatabaseClient databaseClient;
  private static final String instanceId = "prowler-instance";
  private static final String databaseId = "prowler-database";
  private static final String projectId = "prowler-spanner";

  public void initialize() {
    SpannerOptions options = SpannerOptions.newBuilder()
        .setProjectId(projectId)
        .setEmulatorHost("http://localhost:9020")
        .build();
    Spanner spanner = options.getService();
    this.databaseClient =  spanner.getDatabaseClient(DatabaseId.of(options.getProjectId(), instanceId, databaseId));
  }

  public void read() {
    ResultSet resultSet = databaseClient.singleUse().executeQuery(Statement.of("select * from JunkTableName"));
    System.out.println("\n\nResults:");
    // Prints the results
    while (resultSet.next()) {
      System.out.println("result line = " + resultSet.getCurrentRowAsStruct());
    }
  }

  // ******************* New Code below this **************************

  static class Singer {

    final long singerId;
    final String firstName;
    final String lastName;

    Singer(long singerId, String firstName, String lastName) {
      this.singerId = singerId;
      this.firstName = firstName;
      this.lastName = lastName;
    }
  }

  static class Album {

    final long singerId;
    final long albumId;
    final String albumTitle;

    Album(long singerId, long albumId, String albumTitle) {
      this.singerId = singerId;
      this.albumId = albumId;
      this.albumTitle = albumTitle;
    }
  }

  private static final String EMULATOR_HOST = System.getenv("SPANNER_EMULATOR_HOST");
  private static final String INSTANCE_ID = "prowler-instance";
  private static final String DATABASE_ID = "prowler-database";
  private static final String PROJECT_ID = "prowler-spanner";
  private static DatabaseClient dbClient;
  private static DatabaseAdminClient dbAdminClient;
  // private static InstanceAdminClient instanceAdminClient;
  private static DatabaseId prowlerDbId;

  private static final String SINGER_TABLE_DDL = "CREATE TABLE Singers ("
      + "  SingerId   INT64 NOT NULL,"
      + "  FirstName  STRING(1024),"
      + "  LastName   STRING(1024),"
      + "  SingerInfo BYTES(MAX)"
      + ") PRIMARY KEY (SingerId)";
  private static final String ALBUM_TABLE_DDL = "CREATE TABLE Albums ("
      + "  SingerId     INT64 NOT NULL,"
      + "  AlbumId      INT64 NOT NULL,"
      + "  AlbumTitle   STRING(MAX)"
      + ") PRIMARY KEY (SingerId, AlbumId),"
      + "  INTERLEAVE IN PARENT Singers ON DELETE CASCADE";

  static final List<Singer> SINGERS =
      Arrays.asList(
          new Singer(1, "Marc", "Richards"),
          new Singer(2, "Catalina", "Smith"),
          new Singer(3, "Alice", "Trentor"),
          new Singer(4, "Lea", "Martin"),
          new Singer(5, "David", "Lomond"));

  static final List<Album> ALBUMS =
      Arrays.asList(
          new Album(1, 1, "Total Junk"),
          new Album(1, 2, "Go, Go, Go"),
          new Album(2, 1, "Green"),
          new Album(2, 2, "Forever Hold Your Peace"),
          new Album(2, 3, "Terrified"));

  public static void setup() {
    initializeSpanner();
    createDatabase();
    writeExampleData();
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
    // instanceAdminClient = spanner.getInstanceAdminClient();
  }

  private static void createDatabase() {
    OperationFuture<Database, CreateDatabaseMetadata> opFuture =  dbAdminClient.createDatabase(
        prowlerDbId.getInstanceId().getInstance(),
        prowlerDbId.getDatabase(),
        Arrays.asList(SINGER_TABLE_DDL, ALBUM_TABLE_DDL));
    try {
      Database db = opFuture.get();
      System.out.println("Created database [" + db.getId() + "]");
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private static void writeExampleData() {
    List<Mutation> mutations = new ArrayList<>();
    for (Singer singer : SINGERS) {
      mutations.add(
          Mutation.newInsertBuilder("Singers")
              .set("SingerId")
              .to(singer.singerId)
              .set("FirstName")
              .to(singer.firstName)
              .set("LastName")
              .to(singer.lastName)
              .build());
    }
    for (Album album : ALBUMS) {
      mutations.add(
          Mutation.newInsertBuilder("Albums")
              .set("SingerId")
              .to(album.singerId)
              .set("AlbumId")
              .to(album.albumId)
              .set("AlbumTitle")
              .to(album.albumTitle)
              .build());
    }
    dbClient.write(mutations);
  }

  public static void query() {
    try (ResultSet resultSet =
        dbClient
            .singleUse() // Execute a single read or query against Cloud Spanner.
            .executeQuery(Statement.of("SELECT SingerId, AlbumId, AlbumTitle FROM Albums"))) {
      while (resultSet.next()) {
        System.out.printf(
            "%d %d %s\n", resultSet.getLong(0), resultSet.getLong(1), resultSet.getString(2));
      }
    }
  }
}
