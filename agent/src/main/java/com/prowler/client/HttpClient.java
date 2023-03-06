package com.prowler.client;

import com.squareup.okhttp.OkHttpClient;

public enum HttpClient {
  HTTP_CLIENT;

  private final OkHttpClient okHttpClient;
  HttpClient() {
    this.okHttpClient = new OkHttpClient();
  }

  public OkHttpClient getClient() {
    return okHttpClient;
  }
}
