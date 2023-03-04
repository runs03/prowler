package com.prowler.apis;

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
    return Response.ok("initialized the prowler application").build();
  }
}

