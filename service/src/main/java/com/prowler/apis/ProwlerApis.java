package com.prowler.apis;

import com.prowler.datastore.ProwlerSpannerStore;
import com.prowler.models.Application;
import com.prowler.models.Violation;
import java.time.LocalDateTime;
import java.util.UUID;
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

  @POST
  @Path("/{app-id}/violations")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response reportViolation(@PathParam("app-id") String appId, Violation violation) {
    Violation newViolation = Violation.newBuilder()
        .setViolationId(UUID.randomUUID().toString())
        .setViolationType(violation.getViolationType())
        .setApplicationName(violation.getApplicationName())
        .setRedactedLogLine(violation.getRedactedLogLine())
        .setHostName(violation.getHostName())
        .setViolatiomTimestamp(LocalDateTime.now())
        .build();
    ProwlerSpannerStore.reportViolation(newViolation);
    return Response.ok(newViolation).build();
  }

  @GET
  @Path("/{app-id}/violations/{violation-id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getViolation(@PathParam("app-id") String appId, @PathParam("violation-id") String violationId) {
    Violation violation = ProwlerSpannerStore.getViolation(appId, violationId);
    if (violation == null) {
      return Response.status(HttpStatus.SC_NOT_FOUND).build();
    }
    return Response.ok(violation).build();
  }

  @GET
  @Path("/{app-id}/violations/find")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findViolations(@PathParam("app-id") String appId,
      @QueryParam("start") String start,
      @QueryParam("end") String end,
      @QueryParam("page_size") Integer pageSize,
      @QueryParam("page_token") String pageToken) {
    return Response.ok().build();
  }
}

