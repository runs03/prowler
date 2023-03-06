package com.prowler.agent;

import com.prowler.scrubs.Scrubs;

class DefaultReceiver implements Receiver {

  private final Scrubs scrubs;

  public DefaultReceiver(Scrubs scrubs) {
    this.scrubs = scrubs;
  }

  @Override
  public void receive(String logLine) {
    scrubs.scrub(logLine);
  }
}
