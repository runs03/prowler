package com.prowler.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prowler.models.Violation;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

public final class ProwlerApiClient {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String ACCEPT_HEADER = "accept";
  private static final String MEDIA_TYPE_JSON = "application/json";

  private static final String endpoint = "http://localhost:8080";

  public Violation reportViolation(Violation violation) {
    try {
      Request request = new Request.Builder()
          .url(buildUrl(violation))
          .addHeader(ACCEPT_HEADER, MEDIA_TYPE_JSON)
          .addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_JSON)
          .post(RequestBody.create(
              MediaType.parse(MEDIA_TYPE_JSON),
              objectMapper.writeValueAsBytes(violation)))
          .build();
      Response response = HttpClient.HTTP_CLIENT.getClient().newCall(request).execute();
      return objectMapper.readValue(response.body().bytes(), Violation.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildUrl(Violation violation) {
    return endpoint
        + "/ProwlerApp/apis/applications/"
        + violation.getApplicationName()
        + "/violations/";
  }
}
