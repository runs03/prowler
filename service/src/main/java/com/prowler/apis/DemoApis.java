package com.prowler.apis;

import com.prowler.datastore.ProwlerSpannerStore;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/initialize")
public final class DemoApis {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response initialize() {
    ProwlerSpannerStore.setup();
    System.out.println("Initialized the prowler application");
    return Response.ok("Initialization completed successfully").build();
  }
}
