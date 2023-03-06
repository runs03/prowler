package com.prowler.agent;

import com.prowler.client.ProwlerApiClient;
import com.prowler.scrubs.Scrubs;

public final class ProwlerAgent {

  private final ProwlerContext context;
  private final Scrubs scrubs;
  private final Receiver receiver;
  private final Reporter reporter;
  private final ProwlerApiClient apiClient;

  public ProwlerAgent(ProwlerContext context) {
    this.context = context;
    this.apiClient = new ProwlerApiClient();
    this.reporter = new DefaultReporter(context, apiClient);
    this.scrubs = new Scrubs(reporter);
    this.receiver = new DefaultReceiver(scrubs);
  }

  public void scan(String logLine) {
    receiver.receive(logLine);
  }
}
