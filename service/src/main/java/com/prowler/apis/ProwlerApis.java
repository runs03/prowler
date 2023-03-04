package com.prowler.apis;

import com.google.common.collect.ImmutableList;
import com.prowler.datastore.ProwlerSpannerStore;
import com.prowler.models.Application;
import com.prowler.models.FindViolationsResponse;
import com.prowler.models.Violation;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

@Path("/applications")
public final class ProwlerApis {

  private static final Application APP = Application.newBuilder()
      .setName("App-1")
      .setOwner("abc")
      .build();

  private static final Violation V1 = Violation.newBuilder()
      .setViolationId("v1")
      .setApplicationName("APP")
      .setRedactedLogLine("redacted - log - line *****")
      .setViolationType("PERSONAL")
      .build();

  private static final Violation V2 = Violation.newBuilder()
      .setViolationId("v2")
      .setApplicationName("APP")
      .setRedactedLogLine("redacted - log - line -2 *****")
      .setViolationType("FINANCIAL")
      .build();

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createApplication(Application application) {
    ProwlerSpannerStore.createApplication(application);
    return Response.ok().build();
  }


  @GET
  @Path("/{app-id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getApplication(@PathParam("app-id") String appId) {
    Application application = ProwlerSpannerStore.getApplication(appId);
    if (application == null) {
      return Response.status(HttpStatus.SC_NOT_FOUND).build();
    }
    return Response.ok(application).build();
  }
  //
  // @POST
  // @Produces(MediaType.APPLICATION_JSON)
  // public Response reportViolation() {
  //   return Response.ok(V1).build();
  // }

  @GET
  @Path("/{app-id}/violations/find")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findViolations(@PathParam("app-id") String appId,
      @QueryParam("start") String start,
      @QueryParam("end") String end,
      @QueryParam("page_size") Integer pageSize,
      @QueryParam("page_token") String pageToken) {
    return Response.ok(FindViolationsResponse.newBuilder()
        .setViolations(ImmutableList.of(V1, V2))
        .setNextPageToken("p2")
        .build())
        .build();
  }
}

